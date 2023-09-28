package edu.school21.models;

import java.util.Objects;

public class User {

    private final Long identifier;
    private String login;
    private String password;
    private boolean isAuthentication;

    public User(Long identifier, String login, String password, boolean isAuthentication) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.isAuthentication = isAuthentication;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAuthentication() {
        return isAuthentication;
    }

    public void setIsAuthentication(boolean authentication) {
        isAuthentication = authentication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(identifier, user.identifier) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, login);
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAuthentication=" + isAuthentication +
                '}';
    }
}
