package school21.spring.service.constants;

public interface QueriesJdbc {

    String FIND_USER_BY_ID =
                    "select * " +
                    "from users " +
                    "where id = ?";

    String GET_ALL_USERS =
                    "select * " +
                    "from users";

    String SAVE_USER = "insert into users(email) values(?)";

    String UPDATE_USER =
                    "update users " +
                    "set email = ? " +
                    "where id = ?";

    String DELETE_USER = "delete from users where id = ?";


    String FIND_USER_BY_EMAIL =
                    "select * " +
                    "from users " +
                    "where email = ?";
}
