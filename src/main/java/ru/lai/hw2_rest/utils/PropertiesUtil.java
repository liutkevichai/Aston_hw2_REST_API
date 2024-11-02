package ru.lai.hw2_rest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class.getName());
    private Properties properties = new Properties();

    private PropertiesUtil(Properties properties) {
        this.properties = properties;
    }

    public static PropertiesUtil fromFile(String filePath) {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load properties from file: " + filePath, e);
            throw new RuntimeException("Failed to load properties from file: " + filePath, e);
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
