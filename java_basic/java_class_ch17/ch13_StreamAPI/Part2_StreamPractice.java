package ch13_StreamAPI;

import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return name + "(" + score + "점)";
    }
}

public class Part2_StreamPractice {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student("홍길동", 85),
            new Student("김철수", 92),
            new Student("이영희", 78),
            new Student("박민수", 95),
            new Student("최지수", 88)
        );

        System.out.println("=== 80점 이상 학생 ===");
        students.stream()
            .filter(s -> s.getScore() >= 80)
            .forEach(System.out::println);

        System.out.println("\n=== 점수 높은 순 정렬 ===");
        students.stream()
            .sorted((s1, s2) -> s2.getScore() - s1.getScore())
            .forEach(System.out::println);

        System.out.println("\n=== 이름만 추출 ===");
        List<String> names = students.stream()
            .map(Student::getName)
            .collect(Collectors.toList());
        System.out.println(names);

        System.out.println("\n=== 평균 점수 ===");
        double average = students.stream()
            .mapToInt(Student::getScore)
            .average()
            .orElse(0.0);
        System.out.println("평균: " + average);

        System.out.println("\n=== 최고점 학생 ===");
        Optional<Student> topStudent = students.stream()
            .max(Comparator.comparing(Student::getScore));
        topStudent.ifPresent(s -> System.out.println(s));

        System.out.println("\n=== 점수별 그룹화 ===");
        Map<String, List<Student>> gradeMap = students.stream()
            .collect(Collectors.groupingBy(s -> {
                int score = s.getScore();
                if (score >= 90) return "A";
                else if (score >= 80) return "B";
                else return "C";
            }));

        gradeMap.forEach((grade, list) -> {
            System.out.println(grade + "등급: " + list);
        });

        System.out.println("\n=== 이름을 쉼표로 연결 ===");
        String joined = students.stream()
            .map(Student::getName)
            .collect(Collectors.joining(", "));
        System.out.println(joined);

        System.out.println("\n=== Optional 예제 ===");
        Optional<Student> found = students.stream()
            .filter(s -> s.getName().equals("홍길동"))
            .findFirst();

        found.ifPresent(s -> System.out.println("찾음: " + s));

        String studentName = found.map(Student::getName).orElse("없음");
        System.out.println("학생 이름: " + studentName);
    }
}

