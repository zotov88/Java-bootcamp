package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {

    private Long id;
    private String title;
    private User owner;
    private List<Message> messages;
    private List<User> users;

    public Chatroom() {
    }

    public Chatroom(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Chatroom(Long id,
                    String title,
                    User owner,
                    List<Message> messages,
                    List<User> users) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.messages = messages;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return Objects.equals(id, chatroom.id) && Objects.equals(title, chatroom.title) && Objects.equals(owner, chatroom.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, owner);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", owner=" + owner +
                ", messages=" + messages +
                '}';
    }
}
