package com.pillowsuite.service;

import com.pillowsuite.service.consumers.AllTickersConsumer;
import com.pillowsuite.service.consumers.DailyMarketSummaryConsumer;
import com.pillowsuite.service.consumers.TopMoversConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private final TopMoversConsumer TMConsumer;
    private final AllTickersConsumer ATConsumer;
    private final DailyMarketSummaryConsumer DMSConsumer;
    private int consuming = 3;

    public ConsumerService() throws Exception {
        TMConsumer = new TopMoversConsumer();
        ATConsumer = new AllTickersConsumer();
        DMSConsumer = new DailyMarketSummaryConsumer();
    }

    public void startConsumers() throws IOException {
        logger.info("Starting consumers.");
        TMConsumer.consume();
        ATConsumer.consume();
        DMSConsumer.consume();
    }

    public int getConsuming(){
        return consuming;
    }

    public void queueCheckClose() throws IOException, TimeoutException {
        if(TMConsumer.isQueueEmpty()){
            logger.info("Closing Top Movers consumer.");
            TMConsumer.close();
            consuming -= 1;
        }
        if(ATConsumer.isQueueEmpty()){
            logger.info("Closing All Tickers consumer.");
            ATConsumer.close();
            consuming -= 1;
        }
        if(DMSConsumer.isQueueEmpty()){
            logger.info("Closing Daily Market Summary consumer.");
            DMSConsumer.close();
            consuming -= 1;
        }
    }


}
