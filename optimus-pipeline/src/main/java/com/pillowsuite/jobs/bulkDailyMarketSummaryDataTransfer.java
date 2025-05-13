package com.pillowsuite.jobs;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.controller.DataTransferController;

public class bulkDailyMarketSummaryDataTransfer {

    private static final Logger logger = LoggerFactory.getLogger(bulkDailyMarketSummaryDataTransfer.class);

    public static void main(String[] args) throws Exception {
        if(args.length > 1){
            LocalDate start = LocalDate.parse(args[0]);
            LocalDate end = LocalDate.parse(args[1]);

            DataTransferController controller = new DataTransferController();
            controller.bulkDailyMarketSummaryDataTransfer(start, end);
        }
        else{
            logger.error("Two valid dates have not been provided.");
        }

    }
}
