import Network.HttpConfig;
import Network.HttpRequestManager;
import Util.JSONConvert;
import Network.MyTCPServer;
import Network.MyUDPServer;

import java.util.ArrayList;
import java.util.List;

public class CarComputer {
    public static void main(String[] args) throws Exception {

        /*
        HttpConfig config = new HttpConfig("C:\\www", 8000);
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("HttpConfig.json");
        try {
            // Serialize Java object info JSON file.
            mapper.writeValue(file, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

        HttpConfig config = JSONConvert.deserialize("HttpConfig.json", HttpConfig.class);



        /*
        File file = new File("HttpConfig.json");
        ObjectMapper mapper = new ObjectMapper();
        HttpConfig config = new HttpConfig("", 0);
        try {
            config = mapper.readValue(file, HttpConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */




        HttpRequestManager httpManager = new HttpRequestManager(config.getRootFolder());

        MyUDPServer sensorServer = new MyUDPServer(4001);
        MyTCPServer httpServer = new MyTCPServer(config.getPort(), httpManager);

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
