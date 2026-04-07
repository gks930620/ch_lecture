package quest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Q4 {
    static class Student {
        String name;
        int score;

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
        List<Student> students = new ArrayList<>();
        students.add(new Student("Kim", 90));
        students.add(new Student("Lee", 100));
        students.add(new Student("Park", 90));

        students.sort(
            Comparator.comparingInt((Student s) -> s.score).reversed()
                .thenComparing(s -> s.name)
        );

        System.out.println(students);
    }
}
