package quest;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

// D-3: TreeSet/TreeMap의 정렬 기준 확인
public class SolutionD3 {
    public static void main(String[] args) {
        // 1) 자연 정렬(Comparable): 넣는 순서와 무관하게 항상 정렬 상태 유지
        Set<Integer> treeSet = new TreeSet<>(Set.of(30, 10, 20));
        System.out.println(treeSet); // [10, 20, 30]

        // 2) 생성자에 Comparator를 주면 그 기준으로 정렬
        Set<Integer> descSet = new TreeSet<>(Comparator.reverseOrder());
        descSet.add(30); descSet.add(10); descSet.add(20);
        System.out.println(descSet); // [30, 20, 10]

        // 3) TreeMap은 "키"를 기준으로 정렬 (값은 무관)
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("banana", 2);
        treeMap.put("apple", 3);
        treeMap.put("cherry", 1);
        System.out.println(treeMap); // {apple=3, banana=2, cherry=1}

        // 핵심: TreeSet/TreeMap은 삽입 시점마다 정렬 위치를 찾아 넣는다(O(log n)).
    }
}
