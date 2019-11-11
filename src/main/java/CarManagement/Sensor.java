package CarManagement;

import java.io.IOException;

public abstract class Sensor {
    protected int carId;

    public Sensor(int carId){
        this.carId = carId;
    }

    public abstract void request(String ip, int port);
    public abstract void sendInfo(String ip, int port) throws IOException;
}
