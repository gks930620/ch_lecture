package quest;

import java.util.ArrayList;
import java.util.List;

// F-2: 부작용 있는 람다 vs 없는 람다 + 간단 테스트 코드
public class SolutionF2 {
    // 부작용 있는 버전: 외부 리스트를 변경 (호출 순서, 재실행에 취약)
    static List<Integer> sideEffectVersion(List<Integer> src) {
        List<Integer> out = new ArrayList<>();
        src.forEach(x -> { if (x > 0) out.add(x * 2); }); // 외부 상태 수정
        return out;
    }

    // 부작용 없는 버전: 입력 -> 새 리스트 반환
    static List<Integer> pureVersion(List<Integer> src) {
        return src.stream().filter(x -> x > 0).map(x -> x * 2).toList();
    }

    static void assertEquals(Object expected, Object actual, String name) {
        if (expected.equals(actual)) System.out.println("PASS: " + name);
        else System.out.println("FAIL: " + name + " expected=" + expected + " actual=" + actual);
    }

    public static void main(String[] args) {
        List<Integer> input = List.of(-1, 2, 3);

        // 순수 함수는 같은 입력이면 몇 번을 불러도 같은 결과 (테스트가 쉬움)
        assertEquals(List.of(4, 6), pureVersion(input), "pure 1회차");
        assertEquals(List.of(4, 6), pureVersion(input), "pure 2회차");

        // 부작용 버전도 이 예제에선 결과가 같지만,
        // out을 공유하거나 병렬화하는 순간 결과가 깨질 수 있다.
        assertEquals(List.of(4, 6), sideEffectVersion(input), "sideEffect");
    }
}
