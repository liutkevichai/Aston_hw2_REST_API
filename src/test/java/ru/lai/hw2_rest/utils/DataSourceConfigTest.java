package ru.lai.hw2_rest.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class DataSourceConfigTest {

    private static MockedStatic<PropertiesUtil> mockedPropertiesUtil;
    private static MockedStatic<DriverManager> mockedDriverManager;

    @BeforeAll
    static void setup() {
        mockedPropertiesUtil = mockStatic(PropertiesUtil.class);
        mockedDriverManager = mockStatic(DriverManager.class);
    }

    @Test
    void testGetConnection_SuccessfulConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:postgresql://localhost:5432/testdb");
        properties.setProperty("user", "testuser");
        properties.setProperty("password", "testpassword");

        mockedPropertiesUtil.when(() -> PropertiesUtil.fromFile("config.properties"))
                .thenReturn(new PropertiesUtil(properties));

        Connection mockConnection = Mockito.mock(Connection.class);
        mockedDriverManager.when(() -> DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb", "testuser", "testpassword"))
                .thenReturn(mockConnection);

        Connection connection = DataSourceConfig.getConnection();
        assertNotNull(connection);
        assertEquals(mockConnection, connection);
    }

    @Test
    void testGetConnection_DriverNotFound() {
        mockedPropertiesUtil.when(() -> PropertiesUtil.fromFile("config.properties"))
                .thenThrow(new RuntimeException("Properties file not found: config.properties"));

        Exception exception = assertThrows(SQLException.class, DataSourceConfig::getConnection);
        assertTrue(exception.getMessage().contains("PostgreSQL Driver not found!"));
    }

    @Test
    void testGetConnection_InvalidCredentials() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:postgresql://localhost:5432/testdb");
        properties.setProperty("user", "invalid_user");
        properties.setProperty("password", "invalid_password");

        mockedPropertiesUtil.when(() -> PropertiesUtil.fromFile("config.properties"))
                .thenReturn(new PropertiesUtil(properties));

        mockedDriverManager.when(() -> DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb", "invalid_user", "invalid_password"))
                .thenThrow(new SQLException("Invalid database credentials"));

        SQLException exception = assertThrows(SQLException.class, DataSourceConfig::getConnection);
        assertTrue(exception.getMessage().contains("Invalid database credentials"));
    }
}
