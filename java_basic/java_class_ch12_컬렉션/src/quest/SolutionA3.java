package quest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// A-3: 중복 요소를 유지한 채 빈도수 계산
public class SolutionA3 {
    public static void main(String[] args) {
        List<String> fruits = List.of("apple", "banana", "apple", "cherry", "banana", "apple");

        // LinkedHashMap: 처음 등장한 순서대로 출력이 고정됨
        Map<String, Integer> freq = new LinkedHashMap<>();
        for (String f : fruits) {
            freq.merge(f, 1, Integer::sum); // 없으면 1, 있으면 기존 값 + 1
        }

        System.out.println(freq); // {apple=3, banana=2, cherry=1}
    }
}
