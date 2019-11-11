import Network.MyUDPServer;

import java.io.IOException;

public class CarComputer {
    public static void main(String[] args) throws IOException {
        MyUDPServer server = new MyUDPServer(4001);
        server.init();
        server.run();
    }
}
