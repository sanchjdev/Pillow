package com.pillowsuite.service.consumers;

import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pillowsuite.shared.model.dto.FullMover;
import com.pillowsuite.shared.model.dto.Mover;
import com.pillowsuite.shared.model.enums.RabbitMQueue;

import java.util.ArrayList;
import java.util.List;

public class TopMoversConsumer extends RabbitMqConsumer {

    public TopMoversConsumer() throws Exception {
        rabbitQueue = RabbitMQueue.TOP_MOVERS;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel) {
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
            List<FullMover> fms = mapper.readValue(message, new TypeReference<List<FullMover>>() {});
            List<Mover> moverList = new ArrayList<>();

            for(FullMover fullMover : fms){
                moverList.addAll(fullMover.getMovers());
            }
        };

    }


}