package Comparator;

import java.util.Arrays;
import java.util.Comparator;

public class Compartor메인 {
    public static void main(String[] args) {
        // 매번 클래스를 Comparable하기 어렵고 그때그때 다른기준으로 객체를 정렬하고 싶을 때
        // Comparator를 이용한다.  Compartor는 두 매개변수를 비교한다.
        // Comparable을 구현하면 정렬기준을 반드시 그렇게해야한다는 거고
        // Comparable을 구현하지않고 정렬을 그때그때 다르게 하고싶다면
        // 그때그때 Comparator 익명 구현객체를 만들면 된다.


        Student std1 = new Student("김민지", 21, 70);
        Student std2 = new Student("팜하니", 21, 90);
        Student std3 = new Student("다니엘", 20, 80);
        Student std4 = new Student("강해린", 19, 60);
        Student std5 = new Student("이혜인", 18, 100);
        Student[] studentArr = {std1, std2, std3, std4, std5};
        Arrays.sort(studentArr, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                //외부에 정렬기준을 맡기기때문에 객체 2개가 있을 뿐. Comparable과 같다.
                //o1이 먼저오는객체, o2가 나중에 오는 객체다.
                if (o1.age > o2.age) return -1;
                else if (o1.age < o2.age) return 1;
                else { //나이도 같다면
                    if (o1.score > o2.score) return -1;
                    else if (o1.score < o2.score) return 1;
                    else return 0;
                }
            }
        });
        for (Student std : studentArr ){
            System.out.println(std);
        }

    }
}
