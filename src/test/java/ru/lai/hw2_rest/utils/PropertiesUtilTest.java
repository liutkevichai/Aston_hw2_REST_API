package ru.lai.hw2_rest.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesUtilTest {
    private static final String TEST_PROPERTIES = "test.properties";
    private static final String PROPERTY_KEY = "exampleKey";
    private static final String PROPERTY_VALUE = "exampleValue";

    @BeforeEach
    void setUp() {
        Logger logger = Logger.getLogger(PropertiesUtil.class.getName());
        logger.setLevel(Level.OFF);
    }

    @Test
    void testFromFileSuccess() {
        PropertiesUtil propertiesUtil = PropertiesUtil.fromFile(TEST_PROPERTIES);
        assertNotNull(propertiesUtil);
    }

    @Test
    void testFromFileNotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> PropertiesUtil.fromFile("nonexistent.properties"));
        assertEquals("Properties file not found: nonexistent.properties", exception.getMessage());
    }

    @Test
    void testReadExistingProperty() throws IOException {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
        PropertiesUtil propertiesUtil = new PropertiesUtil(properties);

        String result = propertiesUtil.readProperty(PROPERTY_KEY);
        assertEquals(PROPERTY_VALUE, result);
    }

    @Test
    void testReadMissingProperty() {
        Properties properties = new Properties();
        PropertiesUtil propertiesUtil = new PropertiesUtil(properties);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> propertiesUtil.readProperty("missingKey"));
        assertEquals("This property is not specified in the .properties file: missingKey", exception.getMessage());
    }

    @Test
    void testLoadFromStream() throws IOException {
        String propertiesContent = PROPERTY_KEY + "=" + PROPERTY_VALUE;
        InputStream inputStream = new ByteArrayInputStream(propertiesContent.getBytes());

        Properties properties = new Properties();
        properties.load(inputStream);
        PropertiesUtil propertiesUtil = new PropertiesUtil(properties);

        assertEquals(PROPERTY_VALUE, propertiesUtil.readProperty(PROPERTY_KEY));
    }
}
