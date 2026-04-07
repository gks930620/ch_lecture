package P3다형성;

import P3다형성.driver.Autobike;
import P3다형성.driver.Car;
import P3다형성.driver.Driver;
import P3다형성.driver.Vehicle;

public class 매개변수다형성Main {
    public static void main(String[] args) {
        Vehicle autobike=new Autobike();
        Vehicle car=new Car();
        Vehicle vehicle=new Vehicle();

        Driver driver=new Driver();
        driver.drive(vehicle);
        driver.drive(autobike);
        driver.drive(car);


    }
}
