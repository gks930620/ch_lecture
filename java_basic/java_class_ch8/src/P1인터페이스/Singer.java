package P1인터페이스;

public class Singer  implements Singable{
    @Override
    public void sing() {
        System.out.println("가수는 노래만 부를 줄 알면 됨");
        System.out.println("가수가 노래를 부름");
    }
}
