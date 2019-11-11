import Network.IRequestManager;
import Network.MyServer;
import Orchestration.CarOrchestrator;
import Orchestration.MainOrchestrationTask;
import Orchestration.SateliteRequestManager;
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
        IRequestManager sateliteRequestManager = new SateliteRequestManager(orchestrator);

        MyServer server = new MyServer(4000, sateliteRequestManager);
        server.init();
        server.run();
    }
}


