package CarManagement;

import java.util.TimerTask;

public class SensorRequestTask extends TimerTask {
    private Sensor sensor;

    private String ip;
    private int port;

    public SensorRequestTask(Sensor sensor, String ip, int port){
        this.sensor = sensor;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        try{
            sensor.request(ip, port);
            sensor.sendInfo(ip, port);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}