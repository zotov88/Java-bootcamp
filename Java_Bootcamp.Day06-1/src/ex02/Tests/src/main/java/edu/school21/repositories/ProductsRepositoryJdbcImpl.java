package edu.school21.repositories;

import edu.school21.constants.Queries;
import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final Connection connection;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_ALL)) {
            ResultSet resultSet = selectQuery.executeQuery();
            while (resultSet.next()) {
                list.add(new Product(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Optional<Product> optional = Optional.empty();
        try (PreparedStatement selectQuery = connection.prepareStatement(Queries.FIND_BY_ID)) {
            selectQuery.setLong(1, id);
            ResultSet resultSet = selectQuery.executeQuery();
            while (resultSet.next()) {
                optional = Optional.of(new Product(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return optional;
    }

    @Override
    public void update(Product product) {
        if (findById(product.getIdentifier()).isPresent()) {
            try (PreparedStatement updQuery = connection.prepareStatement(Queries.UPDATE)) {
                updQuery.setString(1, product.getName());
                updQuery.setInt(2, product.getPrice());
                updQuery.setLong(3, product.getIdentifier());
                updQuery.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(Product product) {
        try (PreparedStatement updQuery = connection.prepareStatement(Queries.SAVE)) {
            updQuery.setString(1, product.getName());
            updQuery.setInt(2, product.getPrice());
            updQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isPresent()) {
            try (PreparedStatement delQuery = connection.prepareStatement(Queries.DELETE)) {
                delQuery.setLong(1, id);
                delQuery.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
