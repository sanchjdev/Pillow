import com.pillowsuite.controller.DataTransferController;
import com.pillowsuite.service.DataTransferService;
import com.pillowsuite.util.MarketDateUtil;

import java.time.LocalDate;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferService dts = new DataTransferService();
        LocalDate start = LocalDate.parse("2025-04-12");
        LocalDate end = LocalDate.parse("2025-05-12");
        //dts.transferDailyMarketSummary(start, end);
        System.out.println(start + " hello");
//        LocalDate date = LocalDate.parse("2025-05-08");
//        if(MarketDateUtil.hasMarketData(date)){
//            DataTransferService transfer = new DataTransferService();
//            transfer.transferMarketSummaryAndAllTickers(date);
//            transfer.closeConnection();
//        }


//        MoverRequest req = new MoverRequest();
//
//        List<Mover> movers = req.fetchData();
//
//        System.out.println(movers.get(0).getTicker());
//

    }
}

