package CarManagement;

import Network.MyClient;
import Network.MyUDPClient;
import Util.MyVector2D;

import java.io.IOException;

public class LocationSensor extends Sensor{

    private MyVector2D currentLocation;
    private MyUDPClient udpClient;

    public LocationSensor(int carId) {
        super(carId);
        udpClient = new MyUDPClient("localhost", 4001);
    }

    @Override
    public void request(String ip, int port) {
        try {
            MyClient client = new MyClient(ip, port);
            client.send(String.valueOf(carId));
            String response = client.getResponse().trim();
            currentLocation = MyVector2D.parse(response);
            System.out.println(response);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void sendInfo(String ip, int port) throws IOException {
        try{
            String locationData = "LOC:" + currentLocation.toString();
            udpClient.send(locationData);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

