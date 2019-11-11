import CarManagement.LocationSensor;
import CarManagement.Sensor;
import CarManagement.SensorRequestTask;

import java.util.Timer;
import java.util.TimerTask;

public class SensorTest2 {
    public static void main(String[] args) throws Exception {
        Sensor loc = new LocationSensor(1);

        Timer timer = new Timer();
        TimerTask task = new SensorRequestTask(loc, "localhost", 4000);

        timer.schedule(task, 2000, 5000);
    }
}
