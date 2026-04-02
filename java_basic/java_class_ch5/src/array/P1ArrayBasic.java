package array;

import java.util.Arrays;

public class P1ArrayBasic {
    public static void main(String[] args) {
        System.out.println("=== 1차원 배열 ===");

        // 배열 선언과 초기화
        int[] scores = {88, 92, 85, 90, 78};

        // 배열 요소 접근
        System.out.println("첫 번째 점수: " + scores[0]);
        System.out.println("배열 길이: " + scores.length);

        // 배열 순회
        System.out.println("\n전체 점수:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println((i + 1) + "번: " + scores[i]);
        }

        // 향상된 for문
        System.out.println("\n향상된 for문:");
        for (int score : scores) {
            System.out.println(score);
        }

        // 합계와 평균
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.length;
        System.out.println("\n합계: " + sum);
        System.out.println("평균: " + average);

        // 최댓값, 최솟값
        int max = scores[0];
        int min = scores[0];
        for (int score : scores) {
            if (score > max) max = score;
            if (score < min) min = score;
        }
        System.out.println("최고점: " + max);
        System.out.println("최저점: " + min);

        // Arrays 클래스 활용
        System.out.println("\n=== Arrays 클래스 ===");
        int[] numbers = {5, 2, 8, 1, 9, 3};
        System.out.println("정렬 전: " + Arrays.toString(numbers));

        Arrays.sort(numbers);
        System.out.println("정렬 후: " + Arrays.toString(numbers));

        int index = Arrays.binarySearch(numbers, 5);
        System.out.println("5의 인덱스: " + index);
    }
}

