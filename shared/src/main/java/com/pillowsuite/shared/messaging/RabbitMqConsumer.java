package com.pillowsuite.shared.messaging;

import com.pillowsuite.shared.util.PropertiesLoader;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RabbitMqConsumer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private final Channel channel;

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
    public void consume(String queueName) throws IOException {
        channel.queueDeclare(queueName, false, false, false, null);
        DeliverCallback deliverCallback = createDeliverCallback(channel);
        CancelCallback cancelCallback = createCancelCallback();

        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);


    }

    // basic deliverCallback function
    private static DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE: " + message);
        };
    }

    // basic cancelCallback function
    private static CancelCallback createCancelCallback(){
        return consumerTag -> {
            logger.info("CONSUMER CANCELLED: " + consumerTag);
        };
    }



}
