package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.constants.ConnectionData;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class DataSourcePostgres implements DataSource {

    private HikariDataSource hikariDataSource;

    public DataSourcePostgres() {
        setConnectionByHikari(
                ConnectionData.POSTGRES_URL,
                ConnectionData.POSTGRES_USERNAME,
                ConnectionData.POSTGRES_PASSWORD);
    }

    private void setConnectionByHikari(String url, String username, String login) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(login);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        setConnectionByHikari(ConnectionData.POSTGRES_URL, username, password);
        return hikariDataSource.getConnection();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
