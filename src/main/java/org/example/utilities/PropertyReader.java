package org.example.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);
    private static PropertyReader instance;
    private static Properties properties;
    private static final String DEFAULT_FILE_NAME = "application.properties";


    private PropertyReader() {
        initProperty(DEFAULT_FILE_NAME);
    }

    private PropertyReader(String fileName) {
        initProperty(fileName);
    }

    public static synchronized PropertyReader instance() {
        if (instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    public static synchronized PropertyReader instance(String fileName) {
        if (instance == null) {
            instance = new PropertyReader(fileName);
        }
        return instance;
    }

    private static void initProperty(String fileName) {
        try (InputStream is = PropertyReader.class.getClassLoader().getResourceAsStream(fileName)) {
            assert is != null;
            properties = new Properties();
            properties.load(is);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key, String.format("The key %s does not exists!", key));
    }

}