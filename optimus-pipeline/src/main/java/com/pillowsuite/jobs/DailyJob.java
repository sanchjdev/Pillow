package com.pillowsuite.jobs;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.service.DataTransferService;
import com.pillowsuite.service.MarketDateService;


public class DailyJob {

    private static final Logger logger = LoggerFactory.getLogger(DailyJob.class);

    public static void main(String[] args) throws Exception {
        String date = args.length > 0 ? args[0] : String.valueOf(LocalDate.now());
        runDailyJob(date);
    }

    public static void runDailyJob(String date) throws Exception{
        if(MarketDateService.hasMarketData(date)) {
            logger.info("Daily job starting for "  + date + ".");
            DataTransferService dailyJob = new DataTransferService();
            dailyJob.transferMarketSummaryAndAllTickers(date);
            logger.info("Market summary and all US tickers processed.");

            dailyJob.transferTopMovers();
            logger.info("Top movers processed.");

            dailyJob.closeConnection();
            logger.info("Daily job complete.");
        }
        else{
            logger.info("Job will not run. Market does not have data for " + date + ".");
        }
    }
}
