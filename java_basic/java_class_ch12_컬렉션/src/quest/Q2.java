package quest;

import java.util.HashSet;
import java.util.Set;

public class Q2 {
    public static void main(String[] args) {
        Set<Integer> a = new HashSet<>(Set.of(1, 2, 3, 4));
        Set<Integer> b = new HashSet<>(Set.of(3, 4, 5, 6));

        Set<Integer> intersection = new HashSet<>(a);
        intersection.retainAll(b);

        Set<Integer> union = new HashSet<>(a);
        union.addAll(b);

        System.out.println("교집합: " + intersection);
        System.out.println("합집합: " + union);
    }
}
