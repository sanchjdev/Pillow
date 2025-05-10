package com.pillowsuite.controller;

import com.pillowsuite.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerController {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    private final ConsumerService service;


    public ConsumerController() throws Exception {
        service = new ConsumerService();
    }

    public void runConsumerService() throws IOException, TimeoutException {
        logger.info("Starting Consumer Service.");
        service.startConsumers();
        do{
            logger.info("Checking queues.");
            service.queueCheckClose();
        } while(service.getConsuming() > 0);

        logger.info("Consumer Service ending.");
    }
}
