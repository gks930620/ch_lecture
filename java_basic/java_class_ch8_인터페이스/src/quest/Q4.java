package quest;

import java.util.Arrays;

public class Q4 {
    static class Student implements Comparable<Student> {
        private final String name;
        private final int score;

        Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public int compareTo(Student other) {
            return Integer.compare(other.score, this.score);
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
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));
    }
}
