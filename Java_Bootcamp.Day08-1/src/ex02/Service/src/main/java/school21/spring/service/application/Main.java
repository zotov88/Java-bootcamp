package school21.spring.service.application;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        startInit("schema.sql", "data.sql");
        ApplicationContext context = new AnnotationConfigApplicationContext("school21.spring.service");
        UsersRepository repositoryJdbc = context.getBean("usersRepositoryJdbcImpl", UsersRepository.class);
        UsersRepository repositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplateImpl", UsersRepository.class);
        UsersService service = context.getBean(UsersService.class);

        System.out.println("repositoryJdbc.findAll()");
        System.out.println(repositoryJdbc.findAll());
        System.out.println("------------------------------------------");
        System.out.println("repositoryJdbcTemplate.findAll()");
        System.out.println(repositoryJdbcTemplate.findAll());
        System.out.println("------------------------------------------");
        System.out.println("service.signUp(\"email_main\")");
        service.signUp("email_main");
        System.out.println("------------------------------------------");
        System.out.println("repositoryJdbc.findAll()");
        System.out.println(repositoryJdbc.findAll());
        System.out.println("------------------------------------------");
        System.out.println("repositoryJdbcTemplate.findAll()");
        System.out.println(repositoryJdbcTemplate.findAll());
    }

    private static void startInit(String... files) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/local_db");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("12345");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            for (String file : files) {
                InputStream input = Main.class.getClassLoader().getResourceAsStream(file);
                assert input != null;
                Scanner scanner = new Scanner(input).useDelimiter(";");
                while (scanner.hasNext()) {
                    statement.executeUpdate(scanner.next().trim());
                }
                scanner.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
