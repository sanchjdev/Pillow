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
    protected static final PropertiesLoader values = new PropertiesLoader("values.properties");
    private final Channel channel;
    private final Connection connection;
    protected RabbitMQueue rabbitQueue;
    protected String message;
    protected String processProp;
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
        int processing = Integer.parseInt(values.get(processProp));
        while(processing != 2){
            values.set(processProp, String.valueOf(processing + 1));
            Thread.sleep(60000);
            processing = Integer.parseInt(values.get(processProp));
            if(processing != 0){
                logger.info(String.format("%s is not processing any messages - check %d", rabbitQueue.getName(), processing));
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
