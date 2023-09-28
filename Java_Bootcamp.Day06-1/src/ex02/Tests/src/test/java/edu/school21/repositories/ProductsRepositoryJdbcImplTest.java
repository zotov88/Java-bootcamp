package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductsRepositoryJdbcImplTest {

    private ProductsRepository repository;
    private EmbeddedDatabase dataSource;

    final List<Product> FIND_ALL = Arrays.asList(
            new Product(1L, "Apple", 100000),
            new Product(2L, "Bread", 30),
            new Product(3L, "Beer", 65),
            new Product(4L, "Egg", 70),
            new Product(5L, "Meat", 300)
    );
    final Product FIND_BY_ID = new Product(2L, "Bread", 30);
    final Product UPDATED = new Product(5L, "Coffee", 500);
    final Product SAVE = new Product(6L, "Fish", 290);
    final List<Product> PRODUCTS_AFTER_DELETE = Arrays.asList(
            new Product(1L, "Apple", 100000),
            new Product(2L, "Bread", 30),
            new Product(4L, "Egg", 70),
            new Product(5L, "Meat", 300)
    );

    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void findAllTest() {
        assertEquals(FIND_ALL, repository.findAll());
    }

    @Test
    public void findByIdTest() {
        assertEquals(Optional.empty(), repository.findById(6L));
        assertEquals(FIND_BY_ID, repository.findById(2L).get());
    }

    @Test
    public void updateTest() {
        repository.update(new Product(5L, "Coffee", 500));
        assertEquals(UPDATED, repository.findById(5L).get());
    }

    @Test
    public void saveTest() {
        repository.save(new Product(6L, "Fish", 290));
        assertEquals(SAVE, repository.findById(6L).get());
    }

    @Test
    public void deleteTest() {
        repository.delete(3L);
        assertFalse(repository.findById(3L).isPresent());
        assertEquals(PRODUCTS_AFTER_DELETE, repository.findAll());
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
