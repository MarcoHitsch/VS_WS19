package Orchestration;

import Util.MyVector2D;

public class Car{
    private int id;
    private MyVector2D location;
    private double speed;
    private MyVector2D direction;

    public int getId(){
        return id;
    }

    public MyVector2D getLocation() {
        return location;
    }

    public Car(MyVector2D location, double speed, MyVector2D direction, int id) {
        this.id = id;
        this.location = location;
        this.speed = speed;
        this.direction = direction.normalize();
    }

    public void simulate() {
        location = location.add(direction.multiply(speed));
    }

    public void print(){
        System.out.println("Car {" + id + "} Loc " + location);
    }
}
