import Network.HttpRequestManager;
import Network.MyTCPClient;
import Network.MyTCPServer;
import Network.MyUDPServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarComputer {
    public static void main(String[] args) throws Exception {
        HttpRequestManager httpManager = new HttpRequestManager();

        MyUDPServer sensorServer = new MyUDPServer(4001);
        MyTCPServer httpServer = new MyTCPServer(8000, httpManager);

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
