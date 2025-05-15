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

    public void queueCheck() throws TimeoutException{
        Thread tmThread = new Thread(() -> {
            try {
                TMConsumer.queueShutdownProcess();
            } catch (InterruptedException | IOException | TimeoutException e) {
                e.printStackTrace();
            }
        });

        Thread atThread = new Thread(() -> {
            try {
                ATConsumer.queueShutdownProcess();
            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        });

        Thread dmsThread = new Thread(() -> {
            try {
                DMSConsumer.queueShutdownProcess();
            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        });

        tmThread.start();
        atThread.start();
        dmsThread.start();
    }

}
