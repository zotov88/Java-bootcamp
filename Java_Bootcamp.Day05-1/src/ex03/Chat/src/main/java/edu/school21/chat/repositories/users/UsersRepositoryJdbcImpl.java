package edu.school21.chat.repositories.users;

import edu.school21.chat.constants.Queries;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public List<User> findAll(int page, int size) {
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.GET_ALL_USERS)) {
            ResultSet resultSet = selectQuery.executeQuery();
            Map<User, ArrayList<ArrayList<Chatroom>>> map = new LinkedHashMap<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setLogin(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                Chatroom ownChatroom = new Chatroom();
                ownChatroom.setId(resultSet.getLong(5));
                ownChatroom.setTitle(resultSet.getString(4));
                Chatroom socChatroom = new Chatroom();
                socChatroom.setId(resultSet.getLong(7));
                socChatroom.setTitle(resultSet.getString(6));

                if (!map.containsKey(user)) {
                    ArrayList<Chatroom> ownRooms = new ArrayList<>();
                    if (ownChatroom.getId() != 0) {
                        ownRooms.add(ownChatroom);
                    }
                    ArrayList<Chatroom> socRooms = new ArrayList<>();
                    if (socChatroom.getId() != 0) {
                        socRooms.add(socChatroom);
                    }
                    ArrayList<ArrayList<Chatroom>> list = new ArrayList<>();
                    list.add(ownRooms);
                    list.add(socRooms);
                    map.put(user, list);
                } else {
                    ArrayList<ArrayList<Chatroom>> list = map.get(user);
                    ArrayList<Chatroom> ownRooms = list.get(0);
                    if (!ownRooms.contains(ownChatroom) && ownChatroom.getId() != 0) {
                        ownRooms.add(ownChatroom);
                    }
                    ArrayList<Chatroom> socRooms = list.get(1);
                    if (!socRooms.contains(socChatroom) && socChatroom.getId() != 0) {
                        socRooms.add(socChatroom);
                    }
                    ArrayList<ArrayList<Chatroom>> newList = new ArrayList<>();
                    newList.add(ownRooms);
                    newList.add(socRooms);
                    map.put(user, newList);
                }
            }
            return appendListsToUsers(map, page, size);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<User> appendListsToUsers(Map<User, ArrayList<ArrayList<Chatroom>>> map,
                                          int page,
                                          int size) {
        List<User> userList = new ArrayList<>();
        int skip = (page) * size;
        for (User user : map.keySet()) {
            if (skip-- <= 0) {
                if (size-- > 0) {
                    ArrayList<ArrayList<Chatroom>> chatroomList = map.get(user);
                    ArrayList<Chatroom> ownRooms = chatroomList.get(0);
                    ArrayList<Chatroom> socRooms = chatroomList.get(1);
                    user.setCreatedRooms(new ArrayList<>(ownRooms));
                    user.setSocializesRooms(new ArrayList<>(socRooms));
                    userList.add(user);
                }
            }
        }
        return userList;
    }

}
