package com.pillowsuite.shared.model.repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.pillowsuite.shared.model.dto.FullMover;
import com.pillowsuite.shared.model.dto.Mover;
import com.pillowsuite.shared.util.StringDataUtil;

public class MoverRepository extends JdbcRepository implements Repository<Mover>{

    private static final String INSERT_SQL =
            "INSERT INTO top_movers (ticker, direction, market_date, change_today, change_perc) VALUES (?, ?, ?, ?, ?)";
    private PreparedStatement stmt;

    public MoverRepository() throws SQLException {

    }

    @Override
    public void save(Mover mover) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
        buildInsertStatement(mover);
        stmt.executeBatch();
    }

    @Override
    public void bulkSave(List<Mover> moverList) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);
        final int BATCH = 2000;
        int count = 0;
        for(Mover mover : moverList){
            buildInsertStatement(mover);
            if(++count % BATCH == 0){
                stmt.executeBatch();
                logger.debug("Insertting " + BATCH + " Mover records.");
            }
        }
        stmt.executeBatch();
        logger.debug("Insertting remaining Mover records.");

    }

    private void buildInsertStatement(Mover mover) throws SQLException {
        java.sql.Date date = StringDataUtil.toSqlDate(mover.getDate());
        float change = StringDataUtil.toFloat2(mover.getTodaysChange());
        float percChange = StringDataUtil.toFloat2(mover.getTodaysChangePerc());

        stmt.setString(1, mover.getTicker());
        stmt.setString(2, mover.getDirection());
        stmt.setDate(3, date);
        stmt.setFloat(4, change);
        stmt.setFloat(5, percChange);

        stmt.addBatch();

    }

}
