package ex05;

public class UserIdsGenerator {

    private static UserIdsGenerator userIdsGenerator;
    private int id = 1;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        if (userIdsGenerator == null) {
            userIdsGenerator = new UserIdsGenerator();
        }
        return userIdsGenerator;
    }

    int generateId() {
        return id++;
    }
}
