package edu.school21.chat.constants;

public interface Query {

    String FIND_MESSAGE_BY_ID = "select * from chat.messages where id = ?";

    String FIND_USER_BY_ID = "select * from chat.users where id = ?";

    String FIND_CHATROOM_BY_ID = "select * from chat.chatrooms where id = ?";
}
