package quest;

import java.util.List;
import java.util.Optional;

// C-3. findFirst 결과를 Optional 안전 처리
// findFirst는 결과가 없을 수 있으므로 Optional을 반환한다. get() 대신 orElse/map으로 "없음" 경로까지 표현한다.
public class SolutionC3 {
    public static void main(String[] args) {
        List<String> names = List.of("kim", "lee", "park");

        Optional<String> found = names.stream()
                .filter(n -> n.startsWith("l"))
                .findFirst();

        // 1) 기본값 대체
        System.out.println(found.orElse("NONE")); // lee

        // 2) 값이 있을 때만 변환
        System.out.println(found.map(String::toUpperCase).orElse("NONE")); // LEE

        // 3) 없는 경우
        String none = names.stream()
                .filter(n -> n.startsWith("z"))
                .findFirst()
                .orElse("NONE");
        System.out.println(none); // NONE
    }
}
