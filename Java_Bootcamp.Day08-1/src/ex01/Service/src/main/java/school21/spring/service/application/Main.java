package school21.spring.service.application;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startInit("schema.sql", "data.sql");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        UsersRepository repository = context.getBean("userRepositoryJdbcImplHikari", UsersRepositoryJdbcImpl.class);
        System.out.println("repository.findById(1L)");
        System.out.println(repository.findById(1L));
        System.out.println("---------------------------------------------");
        System.out.println("repository.findAll()");
        System.out.println(repository.findAll());
        System.out.println("---------------------------------------------");
        System.out.println("repository.save(new User(null, \"email_main_1\"))" + "\nrepository.findByEmail(\"email_main_1\")");
        repository.save(new User(null, "email_main_1"));
        System.out.println(repository.findByEmail("email_main_1"));
        System.out.println("---------------------------------------------");

        System.out.println("switch repository");
        System.out.println("---------------------------------------------");
        repository = context.getBean("userRepositoryJdbcTemplateImplDMDS", UsersRepositoryJdbcTemplateImpl.class);
        System.out.println("repository.save(new User(null, \"email_main_2\"))");
        repository.save(new User(null, "email_main_2"));
        System.out.println("---------------------------------------------");
        System.out.println("repository.findAll()");
        System.out.println(repository.findAll());
        System.out.println("---------------------------------------------");
        System.out.println("repository.update(new User(6L, \"updated_email\"))");
        repository.update(new User(6L, "updated_email"));
        System.out.println("repository.findById(6L)");
        System.out.println(repository.findById(6L));
        System.out.println("---------------------------------------------");
        System.out.println("repository.delete(10L);");
        repository.delete(10L);
        System.out.println("repository.findAll())");
        System.out.println(repository.findAll());
        System.out.println("---------------------------------------------");
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
