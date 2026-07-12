package P2제네릭기본2;

public class Architect  implements Workable<Building>  {
    //인터페이스의 타입파라미터 T를 Building으로 확정해서 구현한 것.
    //이렇게 타입을 확정하면 구현 클래스에는 <>를 다시 선언할 필요가 없음.

    @Override
    public Building work() {
        return new Building();
    }


}
