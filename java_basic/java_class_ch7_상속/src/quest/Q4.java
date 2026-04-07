package quest;

public class Q4 {
    static class Vehicle {
        void move() {
            System.out.println("이동합니다.");
        }
    }

    static class Bus extends Vehicle {
        void openDoor() {
            System.out.println("문을 엽니다.");
        }
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Bus();
        vehicle.move();

        if (vehicle instanceof Bus bus) {
            bus.openDoor();
        }
    }
}
