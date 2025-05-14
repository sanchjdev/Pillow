package com.pillowsuite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.service.requests.AllTickersRequest;
import com.pillowsuite.service.requests.MarketSummaryRequest;
import com.pillowsuite.service.requests.MoverRequest;
import com.pillowsuite.service.requests.TickerOverviewRequest;
import com.pillowsuite.shared.model.dto.*;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.util.MarketDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class DataTransferService {
    private final Logger logger = LoggerFactory.getLogger(DataTransferService.class);
    private final RabbitMqPublisher publisher;
    private final ObjectMapper mapper =  new ObjectMapper();
    private String queueName = null;

    public DataTransferService() throws Exception {
        publisher = new RabbitMqPublisher();
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
            for(int i = 0; i < tickers.size(); i += chunkSize){
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
            logger.info("Retrieving All Tickers from source");
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
    public void DailyMarketSummary(LocalDate date) throws IOException {
        String message = getMarketSummary(date);
        // message has data
        if(!message.equals("")){
            transfer(message);
        }
    }

    public void bulkDailyMarketSummary(LocalDate startDate, LocalDate endDate) throws IOException, InterruptedException {
        int chunkSize = 90;
        int chunk = 1;
        List<LocalDate> marketDates = MarketDateUtil.datesWithMarketDataInRange(startDate, endDate);
        for(int x = 0; x < marketDates.size(); x += chunkSize){
            int end = Math.min(x+chunkSize, marketDates.size());
            List<LocalDate> marketDatesChunk = marketDates.subList(x, end);
            logger.info(String.format("Loading chunk %d", chunk));
            for(LocalDate date : marketDatesChunk){
                DailyMarketSummary(date);
            }
            logger.info(String.format("Completed chunk %d", chunk++));
            // once end equals list size then all marketDates have been chunked
            if(end != marketDates.size()){
                avoidGoAway(80);
            }
        }
    }

    public void topMovers() throws IOException {
        String message = getTopMovers();
        transfer(message);
    }

    // Basically the daily job, this utilizes one api call to fulfill both the
    // tickers and market summary request
    public void transferMarketSummaryAndAllTickers(LocalDate date){
        RabbitMQueue summaryQueue = RabbitMQueue.DAILY_MARKET_SUMMARY;
        RabbitMQueue tickerQueue = RabbitMQueue.ALL_TICKERS;
        try{
            String dateString = date.toString();
            List<String> tickers = new ArrayList<>();
            MarketSummaryRequest request = new MarketSummaryRequest();

            FullMarketSummary fms = request.fetchData(dateString);

            for(MarketSummaryResults summary : fms.getSummaries()){
                tickers.add(summary.getTicker());
            }

            String summaryMessage = mapper.writeValueAsString(fms);
            publisher.publish(summaryQueue.getName(), summaryMessage);

            String tickerMessage = mapper.writeValueAsString(tickers);
            publisher.publish(tickerQueue.getName(), tickerMessage);

        } catch(Exception e){
            logger.error("Issue with publish or MarketSummary message is empty", e);
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

    // New methods that are a bit more dynamic and has better Single Responsibility
    // Methods to get the data and another to transfer to rabbitmq
    private void transfer(String message) throws IOException {
        queueCheck();
        if(!message.equals("")){
            publisher.publish(queueName, message);
        }
        else{
            logger.info("Message is empty.");
        }
    }

    private String getTopMovers(){
        try{
            MoverRequest request = new MoverRequest();
            List<FullMover> movers = request.fetchData();
            return mapper.writeValueAsString(movers);
        } catch(Exception e){
            logger.info("Market does not have data.");
            return "";
        }
    }

    // Returns a json string summary of the market. If data not available returns empty string
    private String getMarketSummary(LocalDate date){
        logger.info("Checking if market has data for " + date + ".");
        if(MarketDateUtil.hasMarketData(date)) {
            try {
                String weekDay = MarketDateUtil.capitalizeFirst(date.getDayOfWeek().toString());
                String dateString = date.toString();
                MarketSummaryRequest request = new MarketSummaryRequest();
                FullMarketSummary fms = request.fetchData(dateString);
                if(fms != null){
                    for(MarketSummaryResults summary : fms.getSummaries()){
                        summary.setWeekDay(weekDay);
                        summary.setMarketDate(dateString);
                    }
                }
                return (fms == null) ? "" : mapper.writeValueAsString(fms);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("", e);
                return "";
            }
        }
        else{
            logger.info("Market does not have data.");
            return "";
        }
    }

    public void setQueueName(RabbitMQueue queue){
        queueName = queue.getName();
    }

    private void queueCheck(){
        if(queueName == null){
            throw new NullPointerException("QueueName has not been set.");
        }
    }

    public void closeConnection() throws IOException, TimeoutException {
        publisher.close();
    }

    private void avoidGoAway(int seconds) throws InterruptedException {
        Thread counter = new Thread(() -> {
            sleepCounter(seconds);
        });

        logger.info(String.format("Sleeping for %d seconds to avoid GO AWAY", seconds));
        counter.start();
        Thread.sleep((long) seconds * 1000);
    }

    private void sleepCounter(int seconds){
        int x = 1;
        try{
            while(x < seconds){
                Thread.sleep(1000);
                logger.info(String.valueOf(x++));
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
