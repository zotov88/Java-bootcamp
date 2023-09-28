package school21.spring.service.constants;

public interface QueriesJdbcTmpl {

    String FIND_USER_BY_ID =
                    "select * " +
                    "from users " +
                    "where id = :id";

    String GET_ALL_USERS =
                    "select * " +
                    "from users";

    String SAVE_USER = "insert into users(password, email) values(:password, :email)";

    String UPDATE_USER =
                    "update users " +
                    "set email = :email " +
                    "where id = :id";

    String DELETE_USER = "delete from users where id = :id";


    String FIND_USER_BY_EMAIL =
                    "select * " +
                    "from users " +
                    "where email = :email";
}
