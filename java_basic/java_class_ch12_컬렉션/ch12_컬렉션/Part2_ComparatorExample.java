package ch8_컬렉션프레임워크;

import java.util.*;

class Student implements Comparable<Student> {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student other) {
        return this.score - other.score;  // 점수 오름차순
    }

    @Override
    public String toString() {
        return name + "(" + score + "점)";
    }
}

public class Part2_ComparatorExample {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("홍길동", 85));
        students.add(new Student("김철수", 92));
        students.add(new Student("이영희", 78));
        students.add(new Student("박민수", 95));

        System.out.println("=== 원본 ===");
        System.out.println(students);

        // Comparable 사용 (점수 오름차순)
        System.out.println("\n=== Comparable (점수 오름차순) ===");
        Collections.sort(students);
        System.out.println(students);

        // Comparator 사용 (점수 내림차순)
        System.out.println("\n=== Comparator (점수 내림차순) ===");
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s2.score - s1.score;
            }
        });
        System.out.println(students);

        // 람다식 사용 (이름 오름차순)
        System.out.println("\n=== 람다식 (이름 오름차순) ===");
        Collections.sort(students, (s1, s2) -> s1.name.compareTo(s2.name));
        System.out.println(students);
    }
}

