package quest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// F-1: Iterator로 순회 중 안전하게 제거
public class SolutionF1 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("apple", "", "banana", " ", "cherry"));

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().isBlank()) {
                it.remove(); // 반복자 자신의 remove -> 안전
            }
        }

        System.out.println(list); // [apple, banana, cherry]

        // 핵심: 순회 중 삭제는 list.remove(...)가 아니라 it.remove()를 써야 예외 없이 안전하다.
    }
}
