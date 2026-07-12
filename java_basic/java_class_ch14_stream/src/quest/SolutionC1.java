package quest;

import java.util.List;

// C-1. reduce로 곱셈 누적
// reduce(초기값, 누적 함수)는 두 값을 하나로 접는 BinaryOperator를 받는다. 곱셈의 항등원 1을 초기값으로 둔다.
public class SolutionC1 {
    public static void main(String[] args) {
        List<Integer> nums = List.of(1, 2, 3, 4, 5);

        int product = nums.stream()
                .reduce(1, (a, b) -> a * b); // ((((1*1)*2)*3)*4)*5

        System.out.println(product); // 120
    }
}
