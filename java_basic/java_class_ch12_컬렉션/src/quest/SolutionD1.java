package quest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// D-1: 사용자 객체 리스트를 나이 오름차순으로 정렬 (Comparator)
public class SolutionD1 {
    static class User {
        private final String name;
        private final int age;

        User(String name, int age) { this.name = name; this.age = age; }
        public String getName() { return name; }
        public int getAge() { return age; }

        @Override
        public String toString() { return name + "(" + age + ")"; }
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>(List.of(
                new User("Kim", 30), new User("Lee", 22), new User("Park", 27)));

        users.sort(Comparator.comparingInt(User::getAge)); // 나이 오름차순

        System.out.println(users); // [Lee(22), Park(27), Kim(30)]

        // 핵심: List.of가 반환하는 불변 리스트는 sort가 불가능하므로 new ArrayList<>(...)로 감싼다.
    }
}
