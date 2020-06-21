package ru.enikhov.Connections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJdbcImpl implements ConnectionJdbc {
    private static final Logger logger = LogManager.getLogger(ConnectionJdbcImpl.class);

    public static final ConnectionJdbc INSTANCE = new ConnectionJdbcImpl();
    private static final String URL = "jdbc:postgresql://localhost:5432/jdbcDB";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1";

    private ConnectionJdbcImpl() {
    }

    public static ConnectionJdbc getInstance() {
        return INSTANCE;
    }

    @Override
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.error("Some thing wrong in getConnection method", e);
        }
        return conn;
    }

}
