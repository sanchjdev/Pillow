package com.pillowsuite.service.consumers;

import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.pillowsuite.shared.model.repository.MoverRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.pillowsuite.shared.model.dto.FullMover;
import com.pillowsuite.shared.model.dto.Mover;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopMoversConsumer extends RabbitMqConsumer {
    protected static final Logger logger = LoggerFactory.getLogger(TopMoversConsumer.class);

    public TopMoversConsumer() throws Exception {
        rabbitQueue = RabbitMQueue.TOP_MOVERS;
    }

    @Override
    protected DeliverCallback createDeliverCallback(Channel channel) {
        return (consumerTag, delivery) -> {
            message = new String(delivery.getBody(), "UTF-8");
            logger.info("RECEIVED MESSAGE FROM " + rabbitQueue.name() + ".");
            queueCheckCycle = 0;
            List<FullMover> fms = mapper.readValue(message, new TypeReference<List<FullMover>>() {});
            List<Mover> moverList = new ArrayList<>();

            for(FullMover fullMover : fms){
                moverList.addAll(fullMover.getMovers());
            }
            try {
                MoverRepository moverRepository = new MoverRepository();
                moverRepository.bulkSave(moverList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

    }


}