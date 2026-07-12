package ch2_variable;

public class P2정수 {
    //정수
    public static void main(String[] args) {
        int var1=0b1011;
        int var2=0206;  // 8진수는 잘 안씀
        int var3=365;   // 기본은 10진수
        int var4=0xB3;  //16진수 :
        byte b1= -128;
        byte b2= 0;
        byte b3= 127;
        //byte b4= 128;  //컴파일 에러 why?

        long l1=10L;   //소문자 l은 숫자 1과 헷갈리니 대문자 L을 사용하세요
        long l2=10;
        long l3= 2_200_000_000L;

        //어떤 진법으로 적어도 출력은 10진수로 나온다
        System.out.println(var1);  // 11
        System.out.println(var2);  // 134
        System.out.println(var3);  // 365
        System.out.println(var4);  // 179
        System.out.println(b1);    // -128
        System.out.println(b2);    // 0
        System.out.println(b3);    // 127
        System.out.println(l1);    // 10
        System.out.println(l2);    // 10
        System.out.println(l3);    // 2200000000
    }
}
