package quest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// E-3: mutable 필드를 해시 키로 썼을 때 문제 재현
public class SolutionE3 {
    static class User {
        final Long id;
        String name; // 가변 필드 (문제의 원인)

        User(Long id, String name) { this.id = id; this.name = name; }
        void setName(String name) { this.name = name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User u)) return false;
            return Objects.equals(id, u.id) && Objects.equals(name, u.name);
        }

        @Override
        public int hashCode() { return Objects.hash(id, name); } // name이 해시에 포함됨
    }

    public static void main(String[] args) {
        Set<User> set = new HashSet<>();
        User u = new User(1L, "Kim");
        set.add(u);
        System.out.println(set.contains(u)); // true (아직 정상)

        u.setName("Lee"); // 저장된 뒤 해시 대상 필드를 변경!

        System.out.println(set.contains(u));                   // false - 같은 객체인데 못 찾음
        System.out.println(set.contains(new User(1L, "Lee"))); // false - 새 값으로도 못 찾음
        System.out.println("size = " + set.size());            // size = 1 - 꺼낼 수 없는 유령 데이터

        // 핵심: 해시 키는 불변으로 설계하라(record 권장).
    }
}
