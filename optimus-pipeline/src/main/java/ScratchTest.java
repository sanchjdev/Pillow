import com.pillowsuite.controller.DataTransferController;
import com.pillowsuite.service.DataTransferService;
import com.pillowsuite.service.requests.MarketSummaryRequest;
import com.pillowsuite.shared.model.dto.FullMarketSummary;
import com.pillowsuite.shared.model.dto.MarketSummaryResults;
import com.pillowsuite.util.MarketDateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferService dts = new DataTransferService();
//        Function<Integer, Integer> add = x -> x + 5;
//
//        System.out.println(add.apply(12));


        LocalDate start = LocalDate.parse("2021-11-22");
        LocalDate end = LocalDate.parse("2025-05-12");

        MarketSummaryRequest req = new MarketSummaryRequest();
        FullMarketSummary fms = req.fetchData("2020-06-30");

        Predicate<String> priceIsRight = p -> Double.parseDouble(p) >= 1 && Double.parseDouble(p) < 1000000;
        Predicate<String> volumeIsRight = v -> v != null && Double.parseDouble(v) >= 100000;
        int count = 0;
        for(MarketSummaryResults result : fms.getSummaries()){
            List<String> values = java.util.Arrays.asList(result.getLow(), result.getHigh(), result.getClose(), result.getOpen());
            if(volumeIsRight.test(result.getVolume()) && values.stream().allMatch(priceIsRight)) {
                System.out.println(++count);

                //!volumeIsRight.test(result.getVolume())
                //!values.stream().allMatch(priceIsRight)
            }
        }
        //dts.transferDailyMarketSummary(start, end);
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

