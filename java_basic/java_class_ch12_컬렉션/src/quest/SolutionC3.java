package quest;

import java.util.LinkedHashMap;
import java.util.Map;

// C-3: entrySet 순회로 키/값 출력
public class SolutionC3 {
    public static void main(String[] args) {
        Map<String, Integer> scores = new LinkedHashMap<>(); // 입력 순서 고정
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 77);

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        // Kim -> 90
        // Lee -> 85
        // Park -> 77

        // 핵심: 키와 값이 모두 필요하면 keySet 순회 + get보다 entrySet 순회가 조회 한 번을 아끼는 정석이다.
    }
}
