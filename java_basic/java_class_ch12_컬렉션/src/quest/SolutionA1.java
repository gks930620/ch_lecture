package quest;

import java.util.Collections;
import java.util.List;

// A-1: 정수 List에서 최댓값/최솟값/평균 구하기
public class SolutionA1 {
    public static void main(String[] args) {
        List<Integer> nums = List.of(5, 3, 8, 1, 9, 4);

        int max = Collections.max(nums);
        int min = Collections.min(nums);

        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        double avg = (double) sum / nums.size(); // 정수끼리 나누면 소수부가 버려지므로 캐스팅 필요

        System.out.println("최댓값: " + max); // 최댓값: 9
        System.out.println("최솟값: " + min); // 최솟값: 1
        System.out.println("평균: " + avg);   // 평균: 5.0
    }
}
