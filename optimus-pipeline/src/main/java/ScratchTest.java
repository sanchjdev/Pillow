import com.pillowsuite.controller.DataTransferController;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferController transfer = new DataTransferController();
        transfer.transferTopMovers();
        transfer.closeConnection();

//        MoverRequest req = new MoverRequest();
//
//        List<Mover> movers = req.fetchData();
//
//        System.out.println(movers.get(0).getTicker());
//

    }
}

