package com.pillowsuite.shared.model.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.pillowsuite.shared.model.dto.Mover;
import com.pillowsuite.shared.util.DataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoverRepository extends JdbcRepository implements Repository<Mover>{

    private static final Logger logger = LoggerFactory.getLogger(MoverRepository.class);

    private static final String INSERT_SQL =
            "INSERT INTO top_movers (ticker, market_date, direction, change_today, change_perc) "
            + "VALUES (?, ?, ?, ?, ?) "
            + "ON DUPLICATE KEY UPDATE  "
            + "ticker = VALUES(ticker),"
            + "market_date = VALUES(market_date),"
            + "direction = VALUES(direction),"
            + "change_today = VALUES(change_today),"
            + "change_perc = VALUES(change_perc);";

    private PreparedStatement stmt;

    public MoverRepository() throws SQLException {

    }

    @Override
    public void save(Mover mover) throws SQLException {
        stmt = conn.prepareStatement(INSERT_SQL);
        buildInsertStatement(mover);
        stmt.executeBatch();
    }

    @Override
    public void bulkSave(List<Mover> moverList) throws SQLException {
        stmt = conn.prepareStatement(INSERT_SQL);
        for(Mover mover : moverList){
            buildInsertStatement(mover);
        }
        logger.debug("Saving mover records.");
        stmt.executeBatch();

    }

    @Override
    public void buildInsertStatement(Mover mover) throws SQLException {
        java.sql.Date date = DataUtil.toSqlDate(mover.getDate());
        float change = DataUtil.toFloat2(mover.getTodaysChange());
        float percChange = DataUtil.toFloat2(mover.getTodaysChangePerc());

        stmt.setString(1, mover.getTicker());
        stmt.setDate(2, date);
        stmt.setString(3, mover.getDirection());
        stmt.setFloat(4, change);
        stmt.setFloat(5, percChange);

        stmt.addBatch();

    }

}
