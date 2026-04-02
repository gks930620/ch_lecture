package 수학;

import java.util.Random;

public class 수학main {
    public static void main(String[] args) {
        //Math클래스의 메소드는 모두 static

        double v1= Math.ceil(5.3); //올림
        double v2= Math.floor(5.3); // 내림
        double v3= Math.round(5.34567);  //소수 첫째자리에서 반올림
        System.out.println(v3);
        System.out.println( Math.round(5.34567*100) /(double)100 );  //소수 둘째자리까지 얻기

        int max=Math.max(3,5);
        int min=Math.min(3,5);
        
        double randomNum=Math.random()*10;  //0~1사이의 값 * 10 ==> 0~10사이의 값.
        Random random=new Random();
        int randomNum2=random.nextInt(30);  //0~29
    }
}
