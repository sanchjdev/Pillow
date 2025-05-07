package com.pillowsuite.shared.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.shared.util.PropertiesLoader;

public class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private static final String url = config.get("database.url");
    private static final String username = config.get("database.username");
    private static final String password = config.get("database.password");

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, username, password);
    }
}
