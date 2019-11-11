import CarManagement.LocationSensor;
import CarManagement.Sensor;
import CarManagement.SensorRequestTask;
import Network.MyClient;

import java.util.Timer;
import java.util.TimerTask;

public class SensorTest {
    public static void main(String[] args) throws Exception {
        Sensor loc = new LocationSensor(0);

        Timer timer = new Timer();
        TimerTask task = new SensorRequestTask(loc, "localhost", 4000);

        timer.schedule(task, 2000, 5000);
    }
}
