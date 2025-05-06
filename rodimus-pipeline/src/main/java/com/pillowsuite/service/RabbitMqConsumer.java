package com.pillowsuite.service;

import com.pillowsuite.shared.messaging.enums.RabbitMQueue;
import com.pillowsuite.shared.util.PropertiesLoader;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class RabbitMqConsumer {

    protected static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private final Channel channel;
    protected RabbitMQueue rabbitQueue;
    protected String message;

    // Constructor with server details and establishes a connection
    public RabbitMqConsumer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.get("rabbitmq.ip"));
        factory.setUsername(config.get("rabbitmq.username"));
        factory.setPassword(config.get("rabbitmq.password"));

        Connection connection = factory.newConnection();
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



}
