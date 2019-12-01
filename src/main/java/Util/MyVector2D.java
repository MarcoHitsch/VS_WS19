package Util;

public class    MyVector2D {
    private double xPart;
    private double yPart;

    public static MyVector2D parse(String value) throws Exception {

        if(!value.matches("\\{[0-9]+[.][0-9]+\\|[0-9]+[.][0-9]+\\}"))
            throw new Exception("Input: " + value + " ist nicht im Format {x.x|y.y}");

        String cleanedValue = value.trim().replace("{", "").replace("}", "");
        String[] values = cleanedValue.split("\\|");
        double x = Double.parseDouble(values[0]);
        double y = Double.parseDouble(values[1]);

        return new MyVector2D(x, y);
    }

    public double x() { return xPart; }
    public double y() { return yPart; }

    public MyVector2D(double x, double y){
        xPart = x;
        yPart = y;
    }

    public double length(){
        return Math.sqrt(Math.pow(xPart, 2) + Math.pow(yPart, 2));
    }

    public MyVector2D multiply(double value){
        return new MyVector2D(xPart * value, yPart * value);
    }

    public MyVector2D add(MyVector2D value){
        return new MyVector2D(xPart+value.x(), yPart+value.y());
    }

    public MyVector2D normalize(){
        return this.multiply(1/this.length());
    }

    @Override
    public String toString(){
        return "{" + xPart + "|" + yPart + "}";
    }
}

