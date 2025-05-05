package com.pillowsuite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.service.requests.MoverRequest;
import com.pillowsuite.service.MarketDateService;
import com.pillowsuite.service.RabbitMqPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pillowsuite.shared.model.*;
import com.pillowsuite.shared.messaging.enums.RabbitMQueue;
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

    // on hold - hitting some type of limit and getting "GOAWAY" from polygon
    public void transferTickerOverview() throws IOException, TimeoutException {
        RabbitMQueue queue = RabbitMQueue.TICKER_OVERVIEW;
        try{
            AllTickersRequest allTickersRequest = new AllTickersRequest();
            List<String> tickers = allTickersRequest.fetchData();
            List<TickerOverview> overviews = new ArrayList<>();
            System.out.println(tickers.size());
            int chunkSize = 50;
            for(int i = 0; i < tickers.size()/100; i += chunkSize){
                int end = Math.min(i+chunkSize, tickers.size());
                List<String> tickerChunk = tickers.subList(i, end);
                // make request to polygon in chunks
                for(String ticker : tickerChunk){
                    System.out.println(ticker);
                    TickerOverviewRequest tickerOverviewRequest = new TickerOverviewRequest();
                    FullTickerOverview fullTickerOverview = tickerOverviewRequest.fetchData(ticker, String.valueOf(LocalDate.now()));
                    overviews.add(fullTickerOverview.getTicker());
                    Thread.sleep(2000);
                }
                logger.info("Saved Chunk");
                logger.info("Sleeping");
                //Thread.sleep(10000);
            }

            String message = mapper.writeValueAsString(overviews);
            publisher.publish(queue.getName(), message);

        } catch(Exception e){
            e.printStackTrace();
            logger.error("", e);
        }
    }

    // Using MarketSummaryRequest parse ticker symbols and transfer
    // Only US stocks - 11k+ tickers
    public void transferAllTickers() throws IOException, TimeoutException {
        RabbitMQueue queue = RabbitMQueue.ALL_TICKERS;
        try{
            MarketSummaryRequest request = new MarketSummaryRequest();
            FullMarketSummary fms = request.fetchData(String.valueOf(LocalDate.now().minusDays(1)));
            List<MarketSummaryResults> marketSummaries = fms.getSummaries();
            List<String> tickers = new ArrayList<>();

            for(MarketSummaryResults summary : marketSummaries){
                tickers.add(summary.getTicker());
            }

            String tickersString = mapper.writeValueAsString(tickers);
            publisher.publish(queue.getName(), tickersString);

        } catch(Exception e){
            e.printStackTrace();
            logger.error("", e);
        }
    }

    // transfer process to gather today's market data for US stocks
    // date - YYYY-MM-DD
    public void transferDailyMarketSummary(String date){
        RabbitMQueue queue = RabbitMQueue.DAILY_MARKET_SUMMARY;
        logger.info("Checking if market has data for " + date + ".");
        if(MarketDateService.hasMarketData(date)) {
            logger.info("Market has data.");
            try {
                MarketSummaryRequest request = new MarketSummaryRequest();
                FullMarketSummary fms = request.fetchData(date);
                String message = mapper.writeValueAsString(fms);
                publisher.publish(queue.getName(), message);
                System.out.println(message);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("", e);
            }
        }
        else{
            logger.info("Market does not have data.");
        }
    }

    // Combo of transferAllTickers and transfer DailyMarketSummary
    public void transferMarketSummaryAndAllTickers(String date){
        RabbitMQueue summaryQueue = RabbitMQueue.DAILY_MARKET_SUMMARY;
        RabbitMQueue tickerQueue = RabbitMQueue.ALL_TICKERS;
        try{
            List<String> tickers = new ArrayList<>();
            MarketSummaryRequest request = new MarketSummaryRequest();

            FullMarketSummary fms = request.fetchData(date);

            for(MarketSummaryResults summary : fms.getSummaries()){
                tickers.add(summary.getTicker());
            }

            String summaryMessage = mapper.writeValueAsString(fms);
            publisher.publish(summaryQueue.getName(), summaryMessage);

            String tickerMessage = mapper.writeValueAsString(tickers);
            publisher.publish(tickerQueue.getName(), tickerMessage);

        } catch(Exception e){
            e.printStackTrace();
            logger.error("", e);
        }
    }

    // process to call top gainer/losers and move to rabbitmq
    public void transferTopMovers() throws IOException, InterruptedException {
        RabbitMQueue queue = RabbitMQueue.TOP_MOVERS;
        try {
            MoverRequest request = new MoverRequest();
            List<FullMover> movers = request.fetchData();
            String message = mapper.writeValueAsString(movers);
            publisher.publish(queue.getName(), message);

        } catch(Exception e){
        e.printStackTrace();
        logger.error("", e);
        }

    }

    public void closeConnection() throws IOException, TimeoutException {
        publisher.close();
    }

}
