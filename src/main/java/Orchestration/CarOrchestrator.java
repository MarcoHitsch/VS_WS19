package Orchestration;
import Util.StreetConditions;
import Util.MyVector2D;

import java.util.ArrayList;
import java.util.List;

public class CarOrchestrator {
    private int runningId;
    private List<Car> cars;
    private StreetConditions streetConditions;

    public CarOrchestrator(){
        cars = new ArrayList<>();
        runningId = 0;
    }

    public Car getCar(int carId){
        return cars.stream().filter((car) -> car.getId() == carId).findFirst().get();
    }

    public void addCar(MyVector2D location, double speed, MyVector2D direction){
        cars.add(new Car(location, speed, direction, runningId));
        runningId++;
    }

    public void simulate(){
        for(Car car : cars){
            car.simulate();
        }
    }

    public void print(){
        for(Car car : cars){
            car.print();
        }
    }
}

