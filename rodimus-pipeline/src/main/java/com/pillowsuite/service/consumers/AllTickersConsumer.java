package com.pillowsuite.service.consumers;

import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class AllTickersConsumer extends RabbitMqConsumer {

    public AllTickersConsumer() throws Exception{
        rabbitQueue = RabbitMQueue.ALL_TICKERS;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
        };
    }
}
