package quest;

import java.util.ArrayList;
import java.util.List;

// A-2: 중간 삽입/삭제와 인덱스 변화 확인
public class SolutionA2 {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C", "D"));
        System.out.println(list);                    // [A, B, C, D]
        System.out.println("C의 위치: " + list.indexOf("C")); // C의 위치: 2

        list.add(1, "X"); // 인덱스 1에 삽입 -> 뒤 요소들이 한 칸씩 밀림
        System.out.println(list);                    // [A, X, B, C, D]
        System.out.println("C의 위치: " + list.indexOf("C")); // C의 위치: 3

        list.remove(2);   // 인덱스 2(B) 삭제 -> 뒤 요소들이 한 칸씩 당겨짐
        System.out.println(list);                    // [A, X, C, D]
        System.out.println("C의 위치: " + list.indexOf("C")); // C의 위치: 2

        // 핵심: ArrayList 중간 삽입/삭제는 뒤쪽 요소를 모두 이동시키므로 O(n)이다.
    }
}
