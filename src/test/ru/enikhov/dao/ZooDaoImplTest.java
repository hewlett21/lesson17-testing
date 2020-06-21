package ru.enikhov.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
//import ru.enikhov.AppZooTest;
import ru.enikhov.Connections.ConnectionJdbc;
import ru.enikhov.Connections.ConnectionJdbcImpl;
import ru.enikhov.pojo.Animal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ZooDaoImplTest {
    private static final Logger logger = LogManager.getLogger(ZooDaoImplTest.class);

    private ZooDao zooDao;
    private ConnectionJdbc connectionJdbc;
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSetMock;

    @BeforeEach
    void setUp() throws SQLException {
        logger.trace("BeforeEach in ZooDaoImplTest");

        initMocks(this);
        connectionJdbc = spy(ConnectionJdbcImpl.getInstance());
        connection = mock(Connection.class);
        zooDao = spy(new ZooDaoImpl(connectionJdbc));
    }

    @Test
    void addAnimal() throws SQLException {
        when(connectionJdbc.getConnection()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(ZooDaoImpl.INSERT_ANIMAL, Statement.RETURN_GENERATED_KEYS);
        doReturn(resultSetMock).when(preparedStatement).getGeneratedKeys();
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getInt(1)).thenReturn(1);
        Integer id = 1;
        String typeAnimal = "Примат";
        String specAnimal = "Шимпанзе";
        int weight = 80;
        int cage = 310;
        String feed = "банан";
        int volume = 5;
        Animal animal = new Animal(id, typeAnimal, specAnimal, weight, cage, feed, volume);

        int result = zooDao.addAnimal(animal);

        verify(connectionJdbc, times(1)).getConnection();
        verify(connection, atMost(1)).prepareStatement(ZooDaoImpl.INSERT_ANIMAL);
        verify(preparedStatement, times(1)).setString(2, typeAnimal);
        verify(preparedStatement, times(1)).setString(3, specAnimal);
        verify(preparedStatement, times(1)).setInt(4, weight);
        verify(preparedStatement, times(1)).setInt(5, cage);
        verify(preparedStatement, times(1)).setString(6, feed);
        verify(preparedStatement, times(1)).setInt(7, volume);
        verify(preparedStatement, times(1)).executeUpdate();
        assertAll("assert all",
                () -> assertEquals(1, result),
                () -> assertEquals(1, result)
        );
    }

    @Test
    void addAnimalWithSqlException() throws SQLException {
        when(connectionJdbc.getConnection()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(ZooDaoImpl.INSERT_ANIMAL, Statement.RETURN_GENERATED_KEYS);
        doThrow(new SQLException("HELLO!")).when(preparedStatement).executeUpdate();
        int id = 1;
        String typeAnimal = "Raptor";
        String specAnimal = "T-rex";
        int weight = 10000;
        int cage = 500;
        String feed = "Beef";
        int volume = 500;
        Animal animal = new Animal(id, typeAnimal, specAnimal, weight, cage, feed, volume);

        Integer result = assertDoesNotThrow(() -> zooDao.addAnimal(animal));

        verify(connectionJdbc, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(ZooDaoImpl.INSERT_ANIMAL, Statement.RETURN_GENERATED_KEYS);
        verify(preparedStatement, atMost(2)).setString(anyInt(), anyString());
        verify(preparedStatement, times(1)).setInt(4, weight);
        verify(preparedStatement, never()).executeQuery();
        verify(preparedStatement, times(1)).executeUpdate();
        assertEquals(0, result);
    }

}