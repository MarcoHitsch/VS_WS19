import Network.IRequestManager;
import Network.MyTCPServer;
import Orchestration.CarOrchestrator;
import Orchestration.MainOrchestrationTask;
import Orchestration.SatelliteRequestManager;
import Util.MyVector2D;

import java.util.Timer;
import java.util.TimerTask;

public class MainSatelite {
    public static void main(String[] args) throws Exception {
        CarOrchestrator orchestrator = new CarOrchestrator();

        orchestrator.addCar(new MyVector2D(0,0), 3, new MyVector2D(1,0));
        orchestrator.addCar(new MyVector2D(1,1), 2, new MyVector2D(1,1));
        orchestrator.print();

        Timer orchestrationTimer = new Timer();
        TimerTask orchestrationTask = new MainOrchestrationTask(orchestrator);

        orchestrationTimer.schedule(orchestrationTask, 2000, 2000);
        IRequestManager sateliteRequestManager = new SatelliteRequestManager(orchestrator);

        MyTCPServer server = new MyTCPServer(4000, sateliteRequestManager, true);
        server.init();
        server.run();
    }
}


