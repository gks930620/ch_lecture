package quest;

import java.util.ArrayList;
import java.util.List;

// D-3: extends 목록에 add가 제한되는 이유
public class SolutionD3 {
    public static void main(String[] args) {
        List<? extends Number> nums = new ArrayList<Double>(); // 실제로는 Double 리스트일 수 있다!

        // nums.add(1);       // 컴파일 오류: Integer를 Double 리스트에 넣으면 타입이 깨진다
        // nums.add(1.5);     // 컴파일 오류: 컴파일러는 실제 타입이 List<Integer>일 가능성도 배제 못 함
        // nums.add(null)만 유일하게 허용된다 (null은 모든 타입에 안전)

        List<? extends Number> filled = List.of(1, 2.5); // 읽기는 문제 없음
        Number first = filled.get(0);
        System.out.println(first); // 1

        // 핵심: extends의 ?는 "Number의 하위 타입 중 정확히 무엇인지 모르는 어떤 하나"라서
        // 컴파일러가 어떤 값을 넣어도 안전하다고 증명할 수 없어 add를 금지한다.
        // 반면 꺼낸 값은 최소한 Number임이 보장되므로 읽기는 허용된다. (PECS: Producer-Extends)
    }
}
