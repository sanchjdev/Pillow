package com.pillowsuite.shared.model.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TickerRepository extends JdbcRepository implements Repository<List<String>> {

    private static final Logger logger = LoggerFactory.getLogger(TickerRepository.class);

    private static final String INSERT_SQL = "INSERT INTO tickers (ticker, created_date) "
            + "VALUES (?, ?) "
            + "ON DUPLICATE KEY UPDATE "
            + "ticker = VALUES(ticker), "
            + "created_date = VALUES(created_date);";

    private PreparedStatement stmt;

    public TickerRepository() throws SQLException{}

    @Override
    public void save(List<String> tickers) throws SQLException {
        stmt = conn.prepareStatement(INSERT_SQL);
        final int BATCH = 2000;
        int count = 0;
        for(String ticker : tickers){
            buildInsertStatement(ticker);
            if(++count % BATCH == 0){
                stmt.executeBatch();
                logger.debug("Saving " + BATCH + " ticker records.");
            }
        }
        stmt.executeBatch();
        logger.debug("Saving remaining records.");
    }

    public void buildInsertStatement(String ticker) throws SQLException {
        java.sql.Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        stmt.setString(1, ticker);
        stmt.setTimestamp(2, timestamp);

        stmt.addBatch();

    }
}
