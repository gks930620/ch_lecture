package quest;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// E-2: equals/hashCode 미구현 시 문제 재현과 수정
public class SolutionE2 {
    // 문제 재현: equals/hashCode 미구현 -> Object의 기본(주소 비교) 사용
    static class BadUser {
        final Long id;
        final String name;
        BadUser(Long id, String name) { this.id = id; this.name = name; }
    }

    // 수정: equals/hashCode 구현
    static class GoodUser {
        final Long id;
        final String name;
        GoodUser(Long id, String name) { this.id = id; this.name = name; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GoodUser u)) return false;
            return Objects.equals(id, u.id) && Objects.equals(name, u.name);
        }

        @Override
        public int hashCode() { return Objects.hash(id, name); }
    }

    public static void main(String[] args) {
        Set<BadUser> bad = new HashSet<>();
        bad.add(new BadUser(1L, "Kim"));
        bad.add(new BadUser(1L, "Kim")); // 값은 같지만 다른 객체로 취급됨!
        System.out.println("BadUser size = " + bad.size());       // BadUser size = 2 (중복 제거 실패)
        System.out.println(bad.contains(new BadUser(1L, "Kim"))); // false (찾지도 못함)

        Set<GoodUser> good = new HashSet<>();
        good.add(new GoodUser(1L, "Kim"));
        good.add(new GoodUser(1L, "Kim"));
        System.out.println("GoodUser size = " + good.size());       // GoodUser size = 1
        System.out.println(good.contains(new GoodUser(1L, "Kim"))); // true
    }
}
