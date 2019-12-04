import Network.*;
import Util.JSONConvert;

import java.util.ArrayList;
import java.util.List;

public class CarComputer {
    public static void main(String[] args) throws Exception {
        HttpConfig config = JSONConvert.deserializeFromFile("HttpConfig.json", HttpConfig.class);
        HttpRequestManager httpManager = new HttpRequestManager(config.getRootFolder());
        SensorRequestManager sensorManager = new SensorRequestManager(config.getRootFolder() + "\\car.json");

        MyUDPServer sensorServer = new MyUDPServer(4001, sensorManager);
        MyTCPServer httpServer = new MyTCPServer(config.getPort(), httpManager, false);

        sensorServer.init();
        httpServer.init();

        List<Thread> serverThreads = new ArrayList<>();
        serverThreads.add(new Thread(sensorServer));
        serverThreads.add(new Thread(httpServer));

        for(Thread t : serverThreads)
            t.start();

        for(Thread t : serverThreads)
            t.join();
    }


}
