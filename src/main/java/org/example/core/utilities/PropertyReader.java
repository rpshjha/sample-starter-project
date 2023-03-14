package org.example.core.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertyReader {

    private static final Logger log = LogManager.getLogger(PropertyReader.class);

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
            log.error("file not found " + FILE_NAME);
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