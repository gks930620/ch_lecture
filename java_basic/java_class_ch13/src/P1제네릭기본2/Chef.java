package P1제네릭기본2;

public class Chef  implements  Workable<Food>{
    @Override
    public Food work() {
        return new Food();
    }
}
