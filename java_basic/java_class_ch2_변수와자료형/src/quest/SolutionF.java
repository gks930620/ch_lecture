package quest;

import java.util.Arrays;

/**
 * ch2 F. 챌린지 — 정답 예시 (F-1 ~ F-3)
 */
public class SolutionF {
    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }

    // ── F-1. 문자열 숫자 파싱 합계 + 예외 처리 ──
    static void f1() {
        String input = "10,20,abc,30";
        String[] tokens = input.split(",");

        int sum = 0;
        for (String token : tokens) {
            try {
                sum += Integer.parseInt(token);   // 문자열 -> int 변환
            } catch (NumberFormatException e) {
                System.out.println("숫자가 아닌 값 무시: " + token);
            }
        }
        System.out.println("합계: " + sum);
        // 숫자가 아닌 값 무시: abc
        // 합계: 60
    }

    // ── F-2. 문자열 배열 중복 제거 + 길이 내림차순 정렬 ──
    static void f2() {
        String[] words = {"banana", "apple", "banana", "kiwi", "apple", "fig"};

        // 1) 중복 제거: 앞에서부터 훑으며 처음 보는 값만 임시 배열에 담기
        String[] temp = new String[words.length];
        int count = 0;
        for (String word : words) {
            boolean exists = false;
            for (int i = 0; i < count; i++) {
                if (temp[i].equals(word)) {   // 내용 비교는 반드시 equals!
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                temp[count] = word;
                count++;
            }
        }
        String[] unique = Arrays.copyOf(temp, count); // 실제 개수만큼 잘라내기

        // 2) 길이 내림차순 정렬 (선택 정렬)
        for (int i = 0; i < unique.length - 1; i++) {
            for (int j = i + 1; j < unique.length; j++) {
                if (unique[j].length() > unique[i].length()) {
                    String t = unique[i];
                    unique[i] = unique[j];
                    unique[j] = t;
                }
            }
        }

        System.out.println(Arrays.toString(unique));
        // [banana, apple, kiwi, fig]
    }

    // ── F-3. 점수 배열 평균/표준편차 계산 ──
    static void f3() {
        int[] scores = {85, 90, 70, 60, 95};

        // 1) 평균: 합은 int로 모아도 되지만, 나눗셈 결과는 double로 받아야 함
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double average = (double) sum / scores.length;

        // 2) 분산: (각 값 - 평균)² 의 평균
        double squaredDiffSum = 0.0;
        for (int score : scores) {
            double diff = score - average;
            squaredDiffSum += diff * diff;
        }
        double variance = squaredDiffSum / scores.length;

        // 3) 표준편차 = 분산의 제곱근
        double stdDev = Math.sqrt(variance);

        System.out.printf("평균: %.2f%n", average);       // 평균: 80.00
        System.out.printf("표준편차: %.2f%n", stdDev);    // 표준편차: 13.04
    }
}
