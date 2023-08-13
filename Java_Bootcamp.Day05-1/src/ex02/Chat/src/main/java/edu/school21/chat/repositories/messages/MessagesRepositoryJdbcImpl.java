package edu.school21.chat.repositories.messages;

import edu.school21.chat.constants.Queries;
import edu.school21.chat.exeptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private final Connection connection;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_MESSAGE_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            Message message = new Message();
            while (resultSet.next()) {
                message.setId(resultSet.getLong(1));
                message.setAuthor(findUserById(resultSet.getLong(2)));
                message.setRoom(findChatroomById(resultSet.getLong(3)));
                message.setText(resultSet.getString(4));
                message.setDate(resultSet.getTimestamp(5).toLocalDateTime());
            }
            return message.getId() == null ? Optional.empty() : Optional.of(message);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void save(Message message) {
        checkMessage(message);
        try (PreparedStatement addQuery = connection.prepareStatement(Queries.INSERT_MESSAGE)) {
            addQuery.setLong(1, message.getAuthor().getId());
            addQuery.setLong(2, message.getRoom().getId());
            addQuery.setString(3, message.getText());
            addQuery.setTimestamp(4, Timestamp.valueOf(message.getDate()));
            addQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message message) {
        checkMessage(message);
        try (PreparedStatement updQuery = connection.prepareStatement(Queries.UPDATE_MESSAGE)) {
            updQuery.setString(1, message.getText());
            updQuery.setTimestamp(2, (message.getDate() == null) ? null : Timestamp.valueOf(message.getDate()));
            updQuery.setLong(3, message.getId());
            updQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User findUserById(Long id) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_USER_BY_ID)) {
            checkIds(Queries.FIND_USER_BY_ID, id);
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setCreatedRooms(new ArrayList<>());
                user.setSocializesRooms(new ArrayList<>());
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Chatroom findChatroomById(Long id) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_CHATROOM_BY_ID)) {
            checkIds(Queries.FIND_CHATROOM_BY_ID, id);
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            Chatroom chatroom = new Chatroom();

            while (resultSet.next()) {
                chatroom.setId(resultSet.getLong(1));
                chatroom.setTitle(resultSet.getString(2));
            }
            return chatroom;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void checkMessage(Message message) {
        findUserById(message.getAuthor().getId());
        findChatroomById(message.getRoom().getId());
    }

    private void checkIds(String mess, Long id) throws SQLException {
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_USER_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            if (!resultSet.next()) {
                throw new NotSavedSubEntityException("Wrong id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
