package CarManagement;

import Network.MyTCPClient;
import Network.MyUDPClient;
import Util.JSONConvert;
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
            MyTCPClient client = new MyTCPClient(ip, port);
            client.send(String.valueOf(carId));
            String response = client.getResponse().trim();
            currentLocation = MyVector2D.parse(response);
            System.out.println(response);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void sendInfo(String ip, int port) throws IOException {
        try{
            SensorData data = new SensorData(carId, SensorType.Location, currentLocation);
            String locationData = JSONConvert.serialize(data, SensorData.class);
            udpClient.send(locationData);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

