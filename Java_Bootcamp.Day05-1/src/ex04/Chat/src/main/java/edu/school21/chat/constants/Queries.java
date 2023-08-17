package edu.school21.chat.constants;

public interface Queries {

    String FIND_MESSAGE_BY_ID = "" +
            "select * " +
            "from chat.messages " +
            "where id = ?";

    String FIND_USER_BY_ID = "" +
            "select * " +
            "from chat.users " +
            "where id = ?";

    String FIND_CHATROOM_BY_ID = "" +
            "select * " +
            "from chat.chatrooms " +
            "where id = ?";

    String INSERT_MESSAGE = "" +
            "insert into chat.messages(author_id, room_id, text, date) " +
            "values(?,?,?,?)";

    String UPDATE_MESSAGE = "" +
            "update chat.messages " +
            "set text = ?, date = ?, author_id = ?, room_id = ? " +
            "where id = ?";

    String GET_ALL_USERS = "" +
            "select u.*, c.title as own_room, c.id as own_r_id," +
            "ct.title as soc_room, uc.chatroom_id as soc_r_id, us.id, us.login as owner " +
            "from (select * from chat.users) u " +
            "left join chat.chatrooms c on u.id = c.owner_id " +
            "left join chat.users_chatrooms uc on u.id = uc.user_id " +
            "left join chat.chatrooms ct on uc.chatroom_id = ct.id " +
            "left join chat.users us on ct.owner_id = us.id ";
}
