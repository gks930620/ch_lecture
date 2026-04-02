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
        //클래스 없이 직접 객체를 생성하는 기능.단순히 객체를 생성한 것일 뿐 아무일도 일어나지 않음..
        singable.sing();
        singable2.sing();
    }
}
