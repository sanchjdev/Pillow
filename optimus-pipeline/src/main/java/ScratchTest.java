import com.pillowsuite.controller.DataTransferController;
import com.pillowsuite.service.DataTransferService;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferService transfer = new DataTransferService();
        transfer.transferMarketSummaryAndAllTickers("2025-05-07");
        transfer.closeConnection();

//        MoverRequest req = new MoverRequest();
//
//        List<Mover> movers = req.fetchData();
//
//        System.out.println(movers.get(0).getTicker());
//

    }
}

