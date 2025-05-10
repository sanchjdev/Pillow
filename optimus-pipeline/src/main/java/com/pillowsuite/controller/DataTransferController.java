package com.pillowsuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.service.DataTransferService;
import com.pillowsuite.service.requests.MoverRequest;
import com.pillowsuite.util.MarketDateUtil;
import com.pillowsuite.service.RabbitMqPublisher;
import com.pillowsuite.shared.model.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.service.requests.MarketSummaryRequest;
import com.pillowsuite.service.requests.AllTickersRequest;
import com.pillowsuite.service.requests.TickerOverviewRequest;

// All transfer jobs/processes
public class DataTransferController {
    private final Logger logger = LoggerFactory.getLogger(DataTransferController.class);
    private final RabbitMqPublisher publisher;
    private final ObjectMapper mapper =  new ObjectMapper();

    public DataTransferController() throws Exception {
        this.publisher = new RabbitMqPublisher();
    }

    public void runDailyJob(String date) throws Exception{
        if(MarketDateUtil.isEndOfDay()) {
            logger.info("Daily job starting for "  + date + ".");
            DataTransferService dailyJob = new DataTransferService();

            dailyJob.transferTopMovers();
            logger.info("Top movers processed.");

            if(MarketDateUtil.isEndOfDay()){
                dailyJob.transferMarketSummaryAndAllTickers(date);
                logger.info("Market summary and all US tickers processed.");
            } else { logger.info("Not end of day. Market summary and ticker did not process.");}

            dailyJob.closeConnection();
            logger.info("Daily job complete.");
        }
        else{
            logger.info("Job will not run. The market hasn't closed for the day.");
        }
    }

}
