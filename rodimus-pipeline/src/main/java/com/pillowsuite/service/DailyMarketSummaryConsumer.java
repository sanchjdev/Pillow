package com.pillowsuite.service;

import com.pillowsuite.shared.messaging.enums.RabbitMQueue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class DailyMarketSummaryConsumer extends RabbitMqConsumer {

    public DailyMarketSummaryConsumer() throws Exception{
        rabbitQueue = RabbitMQueue.DAILY_MARKET_SUMMARY;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
        };
    }
}
