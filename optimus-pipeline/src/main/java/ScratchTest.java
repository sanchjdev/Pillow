import com.pillowsuite.service.DataTransferProcess;


public class ScratchTest {
    public static void main(String[] args) throws Exception {

        DataTransferProcess transfer = new DataTransferProcess();
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

