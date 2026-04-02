package practice;

import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        double width,height,area,permiter;
        System.out.println("가로의 길이는? (단위 : m)");
        width = scanner.nextDouble();
        System.out.println("세로의 길이는? (단위 : m)");
        height=scanner.nextDouble();

        area=width*height;
        permiter=2*(width+height);



        System.out.println("직사각형의 넓이는?" +  area);
        System.out.println("직사각형의 둘레는?" +  permiter);

    }
}
