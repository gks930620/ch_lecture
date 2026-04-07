package P1제네릭기본2;

public class Architect  implements Workable<Building>  {
    //제네릭타입에서는 클래스에는 <>안씀.  인터페이스에 제네릭이 전부 있기때문에 새로하면 안됨.

    @Override
    public Building work() {
        return new Building();
    }


}
