package P3다형성.driver;

public class Car  extends  Vehicle{
    @Override
    public void run() {
        System.out.println("자동차는  굴러갑니다.");
    }
}
