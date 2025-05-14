package com.pillowsuite.service.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.shared.model.repository.TickerRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.sql.SQLException;
import java.util.List;

public class AllTickersConsumer extends RabbitMqConsumer {

    public AllTickersConsumer() throws Exception{
        rabbitQueue = RabbitMQueue.ALL_TICKERS;
        processProp = "ticker-processing";
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
            values.set(processProp, "0");
            List<String> tickers = mapper.readValue(message, new TypeReference<List<String>>() {});

            try{
                TickerRepository tickerRepository = new TickerRepository();
                tickerRepository.save(tickers);
            } catch (SQLException e){
                logger.error(e.getMessage());
            }

        };
    }
}
