package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import school21.spring.service.constants.QueriesJdbcTmpl;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository("usersRepositoryJdbcTemplateImpl")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> rowMapper;

    public UsersRepositoryJdbcTemplateImpl(@Qualifier("driverManagerDataSource") DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("password"),
                rs.getString("email")
        );
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                QueriesJdbcTmpl.FIND_USER_BY_ID,
                new MapSqlParameterSource().addValue("id", id),
                rowMapper));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(QueriesJdbcTmpl.GET_ALL_USERS, rowMapper);
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update(
                QueriesJdbcTmpl.SAVE_USER,
                new MapSqlParameterSource()
                        .addValue("password", entity.getPassword())
                        .addValue("email", entity.getEmail()),
                new GeneratedKeyHolder());
    }

    @Override
    public void update(User entity) {
        if (findById(entity.getIdentifier()).isPresent()) {
            jdbcTemplate.update(
                    QueriesJdbcTmpl.UPDATE_USER,
                    new MapSqlParameterSource()
                            .addValue("id", entity.getIdentifier())
                            .addValue("password", entity.getPassword())
                            .addValue("email", entity.getEmail())
            );
        }
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(QueriesJdbcTmpl.DELETE_USER, new MapSqlParameterSource().addValue("id", id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                QueriesJdbcTmpl.FIND_USER_BY_EMAIL,
                new MapSqlParameterSource().addValue("email", email),
                rowMapper));
    }
}
