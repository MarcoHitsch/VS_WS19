package CarManagement;

public class SensorData{
    private int carId;
    private SensorType type;
    private Object payLoad;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {
        this.type = type;
    }

    public Object getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(Object payLoad) {
        this.payLoad = payLoad;
    }

    public SensorData(int carId, SensorType type, Object payLoad) {
        this.carId = carId;
        this.type = type;
        this.payLoad = payLoad;
    }

    public SensorData(){}
}
