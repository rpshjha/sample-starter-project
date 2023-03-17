package org.example.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyReader {
    private static final Logger log = LoggerFactory.getLogger(PropertyReader.class);

    private PropertyReader() {
    }

    private static Properties properties = null;
    private static final String FILE_NAME = "application.properties";

    static {
        initProperty();
    }

    private static void initProperty() {
        try {
            try (InputStream is = PropertyReader.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
                assert is != null;
                try (Reader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                    properties = new Properties();
                    properties.load(bufferedReader);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File not found => {}", FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name key
     * @return String
     */
    public static String get(String name) {
        return properties.getProperty(name);
    }
}