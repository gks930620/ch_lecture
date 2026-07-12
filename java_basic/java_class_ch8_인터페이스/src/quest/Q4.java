package quest;

import java.util.Arrays;
import java.util.Comparator;

public class Q4 {
    static class Student {
        private final String name;
        private final int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return name + "(" + score + ")";
        }
    }

    public static void main(String[] args) {
        Student[] students = {
            new Student("Kim", 80),
            new Student("Lee", 95),
            new Student("Park", 88)
        };

        // 기준 1 : 이름 오름차순 정렬 (Comparator 익명 구현 객체)
        Arrays.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        System.out.println("이름순: " + Arrays.toString(students));

        // 기준 2 : 점수 내림차순 정렬 — 같은 배열을 다른 기준으로 다시 정렬
        Arrays.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Integer.compare(o2.score, o1.score);
            }
        });
        System.out.println("점수순: " + Arrays.toString(students));
    }
}
