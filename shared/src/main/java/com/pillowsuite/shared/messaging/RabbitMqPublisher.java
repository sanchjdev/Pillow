package com.pillowsuite.shared.messaging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.pillowsuite.shared.util.PropertiesLoader;

public class RabbitMqPublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqPublisher.class);
    private static final PropertiesLoader config = new PropertiesLoader("config.properties");
    private final Connection connection;
    private final Channel channel;

    public RabbitMqPublisher() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.get("rabbitmq.ip"));
        factory.setUsername(config.get("rabbitmq.username"));
        factory.setPassword(config.get("rabbitmq.password"));

        this.connection = factory.newConnection();
        logger.info("connection created");
        this.channel = connection.createChannel();
        logger.info("channel created");
    }

    public void publish(String queueName, String message) throws IOException {
        logger.info("publishing to " + queueName);
        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        logger.debug("published: " + message);
    }

    public void close() throws IOException, TimeoutException {
        if(channel != null) channel.close();
        if(connection != null) connection.close();
    }

}
