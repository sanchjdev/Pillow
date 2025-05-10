package com.pillowsuite.shared.model.repository;

import com.pillowsuite.shared.model.dto.MarketSummaryResults;
import com.pillowsuite.shared.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class SecurityRepository extends JdbcRepository implements Repository<MarketSummaryResults> {

    private static final Logger logger = LoggerFactory.getLogger(SecurityRepository.class);

    private static final String INSERT_SQL =
    "INSERT INTO securities_eod (ticker, market_date, open, close, high, low, volume, vw, transactions, created_date) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE "
            + "ticker = VALUES(ticker), "
            + "market_date = VALUES(market_date), "
            + "open = VALUES(open), "
            + "close = VALUES(close), "
            + "high = VALUES(high), "
            + "low = VALUES(low), "
            + "volume = VALUES(volume), "
            + "vw = VALUES(vw), "
            + "transactions = VALUES(transactions), "
            + "created_date = VALUES(created_date);";

    private PreparedStatement stmt;

    public SecurityRepository() throws SQLException {}

    @Override
    public void save(MarketSummaryResults marketSummaryResults) throws SQLException {
        stmt = conn.prepareStatement(INSERT_SQL);
    }

    @Override
    public void bulkSave(List<MarketSummaryResults> summaryResultsList) throws SQLException {
        stmt = conn.prepareStatement(INSERT_SQL);
        final int BATCH = 2000;
        int count = 0;

        for(MarketSummaryResults marketSummaryResults : summaryResultsList){
            buildInsertStatement(marketSummaryResults);
            if(++count % BATCH == 0){
                stmt.executeBatch();
                logger.debug("Saving " + BATCH + " security records.");
            }
        }
        stmt.executeBatch();
        logger.debug("Saving remaining records.");
    }

    @Override
    public void buildInsertStatement(MarketSummaryResults marketSummaryResults) throws SQLException{
        java.sql.Date marketDate = DataUtil.toSqlDate(String.valueOf(LocalDate.now()));
        float open = DataUtil.toFloat2(marketSummaryResults.getOpen());
        float close = DataUtil.toFloat2(marketSummaryResults.getClose());
        float high = DataUtil.toFloat2(marketSummaryResults.getHigh());
        float low = DataUtil.toFloat2(marketSummaryResults.getLow());
        int volume = DataUtil.decimalToInt(marketSummaryResults.getVolume());
        float vw = DataUtil.toFloat2(marketSummaryResults.getVwap());
        int transactions = Integer.parseInt(marketSummaryResults.getTransactions() != null ? marketSummaryResults.getTransactions() : "0");
        java.sql.Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());


        stmt.setString(1, marketSummaryResults.getTicker());
        stmt.setDate(2, marketDate);
        stmt.setFloat(3, open);
        stmt.setFloat(4, close);
        stmt.setFloat(5, high);
        stmt.setFloat(6, low);
        stmt.setInt(7, volume);
        stmt.setFloat(8, vw);
        stmt.setInt(9, transactions);
        stmt.setTimestamp(10, timestamp);

        stmt.addBatch();
    }

}
