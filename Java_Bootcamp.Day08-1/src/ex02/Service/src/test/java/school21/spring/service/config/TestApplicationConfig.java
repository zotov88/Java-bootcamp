package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;
import school21.spring.service.services.UsersService;
import school21.spring.service.services.UsersServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class TestApplicationConfig {

    @Bean
    DataSource dataSource() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .build();

        return dataSource;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("usersRepositoryJdbcImpl")
    UsersRepository usersRepositoryJdbcImpl() throws SQLException {
        return new UsersRepositoryJdbcImpl(dataSource());
    }

    @Bean("usersRepositoryJdbcTemplateImpl")
    UsersRepository usersRepositoryJdbcTemplateImpl() {
        return new UsersRepositoryJdbcTemplateImpl(dataSource());
    }


    @Bean("usersServiceJdbc")
    UsersService usersServiceJdbc() throws SQLException {
        return new UsersServiceImpl(usersRepositoryJdbcImpl(), bCryptPasswordEncoder());
    }

    @Bean("usersServiceJdbcTemplate")
    UsersService usersServiceJdbcTemplate() {
        return new UsersServiceImpl(usersRepositoryJdbcTemplateImpl(), bCryptPasswordEncoder());
    }
}