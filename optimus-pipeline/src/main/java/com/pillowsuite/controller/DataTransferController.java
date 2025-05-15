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
    DataTransferService dataTransferService;

    public DataTransferController() throws Exception {
        dataTransferService = new DataTransferService();
    }

    public void runDailyJob(LocalDate date) throws Exception{
        if(MarketDateUtil.hasMarketData(date)) {
            logger.info("Daily job starting for "  + date + ".");

            dataTransferService.transferTopMovers();
            logger.info("Top movers processed.");

            dataTransferService.transferMarketSummaryAndAllTickers(date);
            logger.info("Market summary and all US tickers processed.");

            dataTransferService.closeConnection();
            logger.info("Daily job complete.");
        }
        else{
            logger.info("Job will not run. The market wasn't open today or hasn't closed yet.");
        }
    }

    public void dailyMarketSummaryDataTransfer(LocalDate date) throws IOException, TimeoutException {
        dataTransferService.setQueueName(RabbitMQueue.DAILY_MARKET_SUMMARY);
        dataTransferService.DailyMarketSummary(date);
        dataTransferService.closeConnection();
    }


    public void bulkDailyMarketSummaryDataTransfer(LocalDate startDate, LocalDate endDate) throws IOException, TimeoutException, InterruptedException {
        dataTransferService.setQueueName(RabbitMQueue.DAILY_MARKET_SUMMARY);
        dataTransferService.bulkDailyMarketSummary(startDate, endDate);
        dataTransferService.closeConnection();
    }

    public void topMoversDataTransfer() throws IOException, TimeoutException {
        dataTransferService.setQueueName(RabbitMQueue.TOP_MOVERS);
        dataTransferService.topMovers();
        dataTransferService.closeConnection();
    }

}
