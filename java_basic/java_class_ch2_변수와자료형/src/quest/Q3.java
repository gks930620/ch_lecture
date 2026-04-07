package quest;

import java.util.Scanner;

public class Q3 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println( "원기둥 밑변의 반지름을 입력하시오 ");
        double r=scanner.nextDouble();
        System.out.println( "원기둥의 높이를 입력하시오 ");
        double height= scanner.nextDouble();
        double area=Math.PI*r*r;
        double v= area*height;
        System.out.println("원기둥 밑변의 넓이는 " +area  + "이고 부피는 " + v + "이다");


    }
}
