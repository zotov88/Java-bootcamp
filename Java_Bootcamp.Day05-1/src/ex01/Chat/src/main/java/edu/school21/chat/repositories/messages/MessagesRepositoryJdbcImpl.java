package edu.school21.chat.repositories.messages;

import edu.school21.chat.constants.Query;
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
        try (PreparedStatement selectQuery = connection.prepareStatement(Query.FIND_MESSAGE_BY_ID)) {
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

    private User findUserById(Long id) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Query.FIND_USER_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setCreatedRooms(new ArrayList<>());
                user.setSocializedRooms(new ArrayList<>());
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Chatroom findChatroomById(Long id) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Query.FIND_CHATROOM_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            Chatroom chatroom = new Chatroom();
            while (resultSet.next()) {
                chatroom.setId(resultSet.getLong(1));
                chatroom.setTitle(resultSet.getString(2));
            }
            return chatroom;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
