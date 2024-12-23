package ru.lai.hw2_rest.utils;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class.getName());
    private final Properties properties;

    PropertiesUtil(Properties properties) {
        this.properties = properties;
    }

    public static PropertiesUtil fromFile(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                logger.log(Level.SEVERE, "Could not find properties file: " + fileName);
                throw new RuntimeException("Properties file not found: " + fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load properties from file: " + fileName, e);
            throw new RuntimeException("Failed to load properties from file: " + fileName, e);
        }
        return new PropertiesUtil(properties);
    }

    public String readProperty(String key) {
        String property = properties.getProperty(key);
        if (property != null) {
            return property;
        } else{
            logger.log(Level.WARNING, "Property '{0}' is not specified in the .properties file", key);
            throw new RuntimeException("This property is not specified in the .properties file: " + key);
        }
    }
}
