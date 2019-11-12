package Orchestration;

import java.util.TimerTask;

public class MainOrchestrationTask extends TimerTask {

    private CarOrchestrator orchestrator;

    public MainOrchestrationTask(CarOrchestrator orchestrator){
        this.orchestrator = orchestrator;
    }

    @Override
    public void run() {
        orchestrator.simulate();
        // orchestrator.print();
    }
}

