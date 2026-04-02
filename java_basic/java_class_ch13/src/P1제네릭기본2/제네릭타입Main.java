package P1제네릭기본2;

public class 제네릭타입Main {
    public static void main(String[] args) {
        //제네릭타입의 인터페이스 상속받은 클래스...지만 제네릭과 똑같음.
        Workable<Building> architect=new Architect();
        //Workable과 Workable<Building>은 다름.
        // Architect는 분명히 Workable<Building>을 상속한 녀석
        Building building = architect.work();

        Workable<Food> chef=new Chef();
        Food food = chef.work();
    }
}
