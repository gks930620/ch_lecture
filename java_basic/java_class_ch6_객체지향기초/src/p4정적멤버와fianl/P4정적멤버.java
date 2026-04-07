package p4정적멤버와fianl;

public  class P4정적멤버 {
    // 객체마다  가지고 있을 필요성이 없는 필드 및 메소드는 정적메소드로.
    // '보통' 객체를 만들필요는 없고 특정기능만 제공해주는 클래스를 만들 때
    // 정적 메소드, 정적 필드로 만든다.

    // 정적 요소는 객체를 생성하지 않고, 클래스이름으로 접근가능하다.
    // static이라는 예약어를 통해 정적 멤버임을 선언함.
    //대표적인 예 Math클래스
    public static void main(String[] args) {
        //Math.PI=3.14;     PI처럼 값을 못 바꾸게 할 때 final로 선언
        double pi=Math.PI;
        int a=3;
        int b=5;
        int bigger= Math.max(a,b);
        System.out.println(bigger);
    }




}
