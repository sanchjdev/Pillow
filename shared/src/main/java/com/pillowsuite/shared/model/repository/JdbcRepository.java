package com.pillowsuite.shared.model.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.pillowsuite.shared.infrastructure.DatabaseConnection;
import com.pillowsuite.shared.util.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class JdbcRepository {

    protected static final Logger logger = LoggerFactory.getLogger(JdbcRepository.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    protected static Connection conn;

    JdbcRepository() throws SQLException {
        conn = DatabaseConnection.getConnection();
    }
}
