package quest;

import java.util.ArrayList;
import java.util.List;

// E-1. 외부 리스트를 forEach로 수정하는 나쁜 예와 개선
// 외부 가변 상태에 의존하면 병렬화 시 결과가 깨질 수 있다. 수집은 최종 연산(toList)에 맡긴다.
public class SolutionE1 {
    public static void main(String[] args) {
        List<Integer> nums = List.of(-2, 3, -1, 5, 7);

        // 나쁜 예: 스트림 밖의 리스트를 forEach로 채움 (부작용)
        List<Integer> bad = new ArrayList<>();
        nums.stream().filter(x -> x > 0).forEach(bad::add);
        System.out.println(bad); // [3, 5, 7]  — 동작은 하지만 병렬화하면 위험

        // 개선: 파이프라인이 스스로 결과를 만들어 반환 (부작용 없음)
        List<Integer> good = nums.stream().filter(x -> x > 0).toList();
        System.out.println(good); // [3, 5, 7]
    }
}
