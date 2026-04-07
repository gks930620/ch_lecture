package ch2_variable;

public class P6자동타입변환 {
    public static void main(String[] args) {
        //자동타입변환, 강제타입변환,  연산식에서의 자동타입변환
        byte b=10;
        int num= b;   // byte -> int ,
        System.out.println("byte -> int : " + num);

        char ch='A';
        num=ch;
        System.out.println("char -> int  : " + num );

        long longValue= num;
        System.out.println("int -> long : " + longValue);

        byte b2=-128;
        int num2= b2;   // 비트만 그대로 가져가는게 아니라 값을 유지시켜줌
        System.out.println("byte -> int : " + num2);
        System.out.println(Integer.toBinaryString(num2));

    }
}
