package ex04;

import java.util.Arrays;
import java.util.Objects;

public class UsersArrayList implements UsersList {

    private int capacity = 10;
    private int index = 0;
    User[] data = new User[capacity];


    @Override
    public void add(User user) {
        if (index >= capacity) {
            capacity *= 2;
            User[] newData = new User[capacity];
            System.arraycopy(data, 0, newData, 0, data.length);
            data = newData;
        }
        data[index++] = user;
    }

    @Override
    public User getById(int id) throws UserNotFoundException {
        for (User user : data) {
            if (!Objects.isNull(user) && user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException("User with id " + id + " is not exist");
    }

    @Override
    public User getByIndex(int index) {
        if (index < 0 || index >= this.index) {
            throw new UserNotFoundException("User with index " + index + " is not exist");
        }
        return data[index];
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public String toString() {
        return "UsersArrayList{" + "data=" + Arrays.toString(data) + '}';
    }
}
