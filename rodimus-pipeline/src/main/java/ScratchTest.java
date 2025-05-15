import java.sql.Connection;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pillowsuite.service.consumers.AllTickersConsumer;
import com.pillowsuite.service.consumers.DailyMarketSummaryConsumer;
import com.pillowsuite.service.consumers.RabbitMqConsumer;
import com.pillowsuite.service.consumers.TopMoversConsumer;
import com.pillowsuite.shared.infrastructure.DatabaseConnection;
import com.pillowsuite.shared.model.enums.RabbitMQueue;
import com.pillowsuite.shared.model.repository.MoverRepository;
import com.pillowsuite.shared.model.repository.TickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        Logger logger = LoggerFactory.getLogger(ScratchTest.class);

        TopMoversConsumer TMConsumer = new TopMoversConsumer();
        AllTickersConsumer ATConsumer = new AllTickersConsumer();
        DailyMarketSummaryConsumer DMSConsumer = new DailyMarketSummaryConsumer();
        TMConsumer.consume();
        ATConsumer.consume();
        DMSConsumer.consume();

        int consuming = 3;
        while(consuming > 0){
            if(TMConsumer.isQueueEmpty()){
                TMConsumer.close();

                consuming -= 1;
            }
            if(ATConsumer.isQueueEmpty()){
                ATConsumer.close();
                consuming -= 1;
            }
            if(DMSConsumer.isQueueEmpty()){
                DMSConsumer.close();
                consuming -= 1;
            }
        }


    }
}