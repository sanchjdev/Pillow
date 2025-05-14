package com.pillowsuite.service.consumers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.pillowsuite.shared.model.dto.MarketSummaryResults;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.shared.model.repository.SecurityRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DailyMarketSummaryConsumer extends RabbitMqConsumer {

    Logger logger = LoggerFactory.getLogger(DailyMarketSummaryConsumer.class);

    public DailyMarketSummaryConsumer() throws Exception{
        rabbitQueue = RabbitMQueue.DAILY_MARKET_SUMMARY;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel){
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
            queueCheckCycle = 0;
            try{
                FullMarketSummary fullMarketSummary = mapper.readValue(message, new TypeReference<>() {});
                SecurityRepository  securityRepository = new SecurityRepository();
                securityRepository.bulkSave(fullMarketSummary.getSummaries());
            } catch(SQLException se){
                se.printStackTrace();
            } catch(Exception e){
                logger.info("Message is empty.");
            }
        };
    }
}
