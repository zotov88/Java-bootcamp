package school21.spring.service.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {

    private final Connection connection;
    private final UsersRepository repositoryJdbcTemplate;
    private final UsersRepository getRepositoryJdbc;
    private final UsersService usersServiceJdbc;
    private final UsersService usersServiceJdbcTemplate;
    private static final String EMAIL_TEST = "email@test.com";

    UsersServiceImplTest() throws SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestApplicationConfig.class);
        connection = context.getBean("dataSource", DataSource.class).getConnection();
        getRepositoryJdbc = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
        repositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        usersServiceJdbc = context.getBean("usersServiceJdbc", UsersService.class);
        usersServiceJdbcTemplate = context.getBean("usersServiceJdbcTemplate", UsersService.class);
    }

    @BeforeEach
    public void init() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("drop table if exists users;");
            st.executeUpdate("create table if not exists users (id integer, password varchar(200), email varchar(40) not null);");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void signedUpWorkWithJdbc() {
        assertNotNull(usersServiceJdbc.signUp(EMAIL_TEST));
        assertEquals(repositoryJdbcTemplate.findByEmail(EMAIL_TEST).get().getEmail(), EMAIL_TEST);
    }

    @Test
    public void signedUpWorkWithJdbcTemplate() {
        assertNotNull(usersServiceJdbcTemplate.signUp(EMAIL_TEST));
        assertEquals(getRepositoryJdbc.findByEmail(EMAIL_TEST).get().getEmail(), EMAIL_TEST);
    }
}