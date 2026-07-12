package P2익명객체구현;

public class 익명객체구현Main {
    public static void main(String[] args) {
        Singable singable=new Singable() {
            @Override
            public void sing() {
                System.out.println("이 singable은 how sweet 노래 부릅니다.");
            }
        };

        Singable singable2=new Singable() {
            @Override
            public void sing() {
                System.out.println("이 singable2은 supernatural 노래 부릅니다.");
            }
        };
        //익명 객체 : 별도 클래스 파일 없이 인터페이스를 그 자리에서 구현해 객체를 만드는 기능.
        //위에서는 객체를 생성만 했을 뿐이라 아직 아무 일도 일어나지 않음. 아래처럼 메소드를 호출해야 실행됨.
        singable.sing();
        singable2.sing();
    }
}
