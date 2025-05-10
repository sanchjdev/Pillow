package com.pillowsuite.service.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.pillowsuite.shared.model.dto.MarketSummaryResults;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.shared.model.repository.SecurityRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.sql.SQLException;
import java.util.List;

public class DailyMarketSummaryConsumer extends RabbitMqConsumer {

    public DailyMarketSummaryConsumer() throws Exception{
        rabbitQueue = RabbitMQueue.DAILY_MARKET_SUMMARY;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");

            FullMarketSummary fullMarketSummary = mapper.readValue(message, new TypeReference<FullMarketSummary>() {});
            try{
                SecurityRepository  securityRepository = new SecurityRepository();
                securityRepository.bulkSave(fullMarketSummary.getSummaries());
            } catch(SQLException e){
                e.printStackTrace();
            }
        };
    }
}
