package P1함수형프로그래밍과람다;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class 함수형람다Main2 {
    public static void main(String[] args) {
//        Calculable calculable= new Calculable() {
//            @Override
//            public void calculate(int x, int y) {
//                System.out.println("aaa");
//            }
//        };
        Calculable calculable= (x, y) -> {
            System.out.println("aaa");
        };


    }
}
