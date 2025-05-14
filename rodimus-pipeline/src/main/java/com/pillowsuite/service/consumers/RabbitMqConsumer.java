package com.pillowsuite.service.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.shared.util.PropertiesLoader;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public abstract class RabbitMqConsumer {

    protected static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private final Channel channel;
    private final Connection connection;
    protected int queueCheckCycle = 0;
    protected RabbitMQueue rabbitQueue;
    protected String message;
    protected ObjectMapper mapper = new ObjectMapper();

    // Constructor with server details and establishes a connection
    public RabbitMqConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.get("rabbitmq.ip"));
        factory.setUsername(config.get("rabbitmq.username"));
        factory.setPassword(config.get("rabbitmq.password"));

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();

    }

    // Consumes from given queue and relies on process function to handle message
    public void consume() throws IOException {
        DeliverCallback deliverCallback = createDeliverCallback(channel);
        CancelCallback cancelCallback = createCancelCallback();

        String consumerTag = channel.basicConsume(rabbitQueue.getName(), true, deliverCallback, cancelCallback);
        //channel.basicCancel(consumerTag);
    }

    // set class level variable - message to message body
    protected abstract DeliverCallback createDeliverCallback(Channel channel);

    // basic cancelCallback function
    private static CancelCallback createCancelCallback(){
        return consumerTag -> {
            logger.info("CONSUMER CANCELLED: " + consumerTag);
        };
    }

    // check if queue is empty
    public boolean isQueueEmpty() throws IOException {
        // if queue is empty return true
        return channel.basicGet(rabbitQueue.getName(), true) == null;
    }

    public void queueShutdownProcess() throws IOException, InterruptedException, TimeoutException {
        while(queueCheckCycle != 12){
            Thread.sleep(5000);
            logger.info(String.format("Checking %s queue.", rabbitQueue.getName()));
            if(isQueueEmpty()){
                queueCheckCycle++;
                logger.info(String.format("%s queue empty - Cycle %d", rabbitQueue.getName(), queueCheckCycle));
            }
        }
        logger.info(String.format("Shutting down %s consumer", rabbitQueue.getName()));
        close();
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }



}
