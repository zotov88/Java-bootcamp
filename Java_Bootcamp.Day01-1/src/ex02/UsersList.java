package ex02;

public interface UsersList {

    void add(User user);

    User getById(int id) throws UserNotFoundException;

    User getByIndex(int index) throws UserNotFoundException;

    int size();

}
