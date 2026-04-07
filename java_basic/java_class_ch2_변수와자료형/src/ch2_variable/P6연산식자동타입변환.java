package ch2_variable;

public class P6연산식자동타입변환 {
    public static void main(String[] args) {
        byte x=10;
        byte y=20;
        byte z= (byte)(x+y);    //  or   (byte)x+byte(y)
        System.out.println(z);

        int a=3;
        int b=2;
        System.out.println("3/2 : " + a/b);   //  3/2 =1.5?
        System.out.println("3/2 : " + (double)a/b);
        System.out.println("3/2 : " + a/(double)b);
        System.out.println("3/2 : " + (double)(a/b));


        //문자열과 더하기
        System.out.println(1+2+"3") ;   
        //이건 언어마다 다름 3을 int로 바꾸고 숫자계산하는언어, 에러가 나는 언어 등등
        System.out.println(1+"2"+3);


    }
}
