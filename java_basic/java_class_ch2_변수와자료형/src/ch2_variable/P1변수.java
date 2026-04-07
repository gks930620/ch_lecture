package ch2_variable;

public class P1변수 {
    public static void main(String[] args) {
        int score=90;
        int x=10;
        int y=x;   //x에 있는 값을 복사해서 y에 저장

        x=20;
        System.out.println(y);  //y에는 x의 값 10을 저장한 것이기 때문에 x가20이 되도 y랑은 관계없음.




        //변수 바꾸기 예제

        int a=3;
        int b=5;
        System.out.println("바꾸기 전 a : " + a );
        System.out.println("바꾸기 전 b : " + b );

        int temp=a;
        a=b;
        b=temp;
        System.out.println("바꾼 후 a : " + a );
        System.out.println("바꾼 후 b : " + b );

    }
}
