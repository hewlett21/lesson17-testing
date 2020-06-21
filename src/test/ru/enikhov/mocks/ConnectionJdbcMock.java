package ru.enikhov.mocks;

import ru.enikhov.Connections.ConnectionJdbc;

import java.sql.Connection;

public class ConnectionJdbcMock implements ConnectionJdbc {
    @Override
    public Connection getConnection() {
        return new ConnectionMock();
    }

}
