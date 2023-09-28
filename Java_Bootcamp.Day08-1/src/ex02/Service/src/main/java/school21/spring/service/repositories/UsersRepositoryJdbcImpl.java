package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import school21.spring.service.constants.QueriesJdbc;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("usersRepositoryJdbcImpl")
public class UsersRepositoryJdbcImpl implements UsersRepository {

    private final Connection connection;


    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = Optional.empty();
        try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.FIND_USER_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.GET_ALL_USERS)) {
            ResultSet resultSet = selectQuery.executeQuery();
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void save(User entity) {
        try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.SAVE_USER)) {
            selectQuery.setString(1, entity.getPassword());
            selectQuery.setString(2, entity.getEmail());
            selectQuery.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        Optional<User> userOptional = findById(entity.getIdentifier());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.UPDATE_USER)) {
                selectQuery.setString(1, user.getEmail());
                selectQuery.setString(2, user.getPassword());
                selectQuery.setLong(3, user.getIdentifier());
                selectQuery.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void delete(Long id) {
        Optional<User> userOptional = findById(id);
        if (userOptional.isPresent()) {
            try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.DELETE_USER)) {
                selectQuery.setLong(1, userOptional.get().getIdentifier());
                selectQuery.execute();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (PreparedStatement selectQuery = connection.prepareStatement(QueriesJdbc.FIND_USER_BY_EMAIL)) {
            selectQuery.setString(1, email);
            ResultSet resultSet = selectQuery.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
