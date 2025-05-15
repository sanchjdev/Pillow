package com.pillowsuite.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private final Properties properties = new Properties();

    public PropertiesLoader(String fileName){
        try(InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)){
            if(input == null){
                throw new IOException("Config File not found in resources: " + fileName);
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }
}
