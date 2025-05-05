import com.pillowsuite.service.DataTransferService;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferService transfer = new DataTransferService();
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

