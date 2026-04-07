package ch2_variable;

public class P6강제타입변환 {
    public static void main(String[] args) {
        int var1=10;
        byte b1= (byte)var1;
        System.out.println("b1 : "+b1);  //10은 그대로 값 유지
        var1=128;

        b1=(byte)var1;
        System.out.println("128을 강제타입변환 : " + b1);

        double d1=3.14;
        int num1= (int) d1;
        System.out.println("double -> int : "+num1);


        float f1=3.14f;
        num1= (int) f1;
        System.out.println("float -> int "+num1);
    }
}
