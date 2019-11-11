package Orchestration;

import Network.IRequestManager;
import Util.MyVector2D;

public class SateliteRequestManager implements IRequestManager {

    String lastRequest;
    int requestedCarId;
    MyVector2D lastLocation;
    CarOrchestrator orchestrator;

    public SateliteRequestManager(CarOrchestrator orchestrator){
        this.orchestrator = orchestrator;
    }

    @Override
    public void receivedRequest(String requestMessage) {
        lastRequest = requestMessage.trim();
        requestedCarId = Integer.valueOf(lastRequest);
        lastLocation = orchestrator.getCar(requestedCarId).getLocation();
    }

    @Override
    public String getResponse() {
        return lastLocation.toString();
    }
}
