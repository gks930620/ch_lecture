package quest;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// B-3: LinkedHashSet과 HashSet의 출력 순서 비교
public class SolutionB3 {
    public static void main(String[] args) {
        List<String> input = List.of("banana", "apple", "cherry", "date");

        Set<String> hashSet = new HashSet<>(input);
        Set<String> linkedSet = new LinkedHashSet<>(input);

        System.out.println("HashSet:       " + hashSet);
        // HashSet: 순서는 다를 수 있음(해시 순서, 보장 안 됨)

        System.out.println("LinkedHashSet: " + linkedSet);
        // LinkedHashSet: [banana, apple, cherry, date] - 항상 입력 순서 유지

        // 핵심: "넣은 순서대로 나와야 한다"는 요구가 있으면 처음부터 LinkedHashSet을 선택한다.
    }
}
