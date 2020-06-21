package ru.enikhov;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import ru.enikhov.Connections.ConnectionJdbc;
import ru.enikhov.dao.ZooDao;
import ru.enikhov.dao.ZooDaoImpl;
import ru.enikhov.mocks.ConnectionJdbcMock;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


class AppZooTest {
    private static final Logger logger = LogManager.getLogger(AppZooTest.class);

    private AppZoo appZoo;
    private ZooDao zooDao;
    private ConnectionJdbc connectionJdbc;

    @BeforeEach
    void setUp() throws SQLException {
        logger.trace("BeforeEach in AppZooTest");
        appZoo = new AppZoo();
        connectionJdbc = new ConnectionJdbcMock();
        zooDao = new ZooDaoImpl(connectionJdbc);
    }


    @Test
    @DisplayName("Тест метода getSumm")
    void getSumm() {
        AppZoo appZoo = new AppZoo();
        double allCost = appZoo.getSumm(100, 250.0);
        double expectedCost = 25000.0;
        assertEquals(allCost, expectedCost);
    }

    @Test
    @DisplayName("ТЕСТ МЕТОДА addMethod")
    void main() {
        assumeTrue(appZoo != null);
        assertDoesNotThrow(() -> appZoo.addMethod(zooDao));
    }


}