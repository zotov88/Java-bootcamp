package school21.spring.service.models;

public class User {

    private Long identifier;
    private String password;
    private String email;

    public User() {
    }

    public User(Long identifier, String password, String email) {
        this.identifier = identifier;
        this.password = password;
        this.email = email;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nUser{" +
                "identifier=" + identifier +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
