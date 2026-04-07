package Comparable;

import java.util.Arrays;

public class Comparable메인 {
    public static void main(String[] args) {
        //Comparable이든 Comparator든 객체를 비교하기 위한 interface
        //Comparable은 자기자신과 다른객체를 비교
        Student std1=new Student("김민지" , 21,70);
        Student std2=new Student("팜하니" , 21,90);
        Student std3=new Student("다니엘" , 20,80);
        Student std4=new Student("강해린" , 19,60);
        Student std5=new Student("이혜인" , 18,100);

        Student[] studentArr={std1,std2,std3,std4,std5};
        Arrays.sort(studentArr);
        //원래는 에러남. 왜? 객체를 정렬할 수 없음.
        // Comparable을 상속받아 Student객체는 기본적으로 나이, 점수를 기준으로 정렬하는 것임을 알려줌.
        for (Student std : studentArr ){
            System.out.println(std);
        }

        //참고 : String 클래스도 Comparable을 구현하고 있음. 그래서 문자열 정렬이 자동으로 되는것임.


    }
}
