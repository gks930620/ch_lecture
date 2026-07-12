package quest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// E-1: User(id, name)를 HashSet에 넣어 중복 판별
public class SolutionE1 {
    static class User {
        private final Long id;
        private final String name;

        User(Long id, String name) { this.id = id; this.name = name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User u)) return false;
            return Objects.equals(id, u.id) && Objects.equals(name, u.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    public static void main(String[] args) {
        Set<User> users = new HashSet<>();
        users.add(new User(1L, "Kim"));
        users.add(new User(2L, "Lee"));
        users.add(new User(1L, "Kim")); // 같은 값 -> 중복으로 판별되어 추가 안 됨

        System.out.println("size = " + users.size());            // size = 2
        System.out.println(users.contains(new User(1L, "Kim"))); // true

        // 핵심: HashSet은 hashCode로 버킷을 찾고 equals로 동일성을 확정한다. 두 메소드를 함께 재정의해야 한다.
    }
}
