package quest;

import java.util.Arrays;

/**
 * ch2 D. String/배열 심화 — 정답 예시
 *
 * D-1 성능 비교, D-2 가변 2차원 배열, D-3 배열 복사 3방식을 실행 코드로 옮겼습니다.
 * D-4(참조 복사 vs 값 복사)는 설명형이라 D-3에서 개념이 함께 드러납니다.
 */
public class SolutionD {
    public static void main(String[] args) {
        d1();
        d2();
        d3();
    }

    // ── D-1. String + vs StringBuilder 성능 비교 ──
    static void d1() {
        int count = 50_000;

        // 1) String + : 매번 새 문자열 객체 생성
        long start1 = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < count; i++) {
            s += "a";
        }
        long time1 = System.currentTimeMillis() - start1;

        // 2) StringBuilder: 내부 버퍼에 이어붙임
        long start2 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("a");
        }
        String result = sb.toString();
        long time2 = System.currentTimeMillis() - start2;

        System.out.println("String +      : " + time1 + "ms"); // 예: 1500ms (환경마다 다름)
        System.out.println("StringBuilder : " + time2 + "ms"); // 예: 2ms    (환경마다 다름)
        System.out.println(s.length() == result.length());     // true (결과 내용은 동일)
    }

    // ── D-2. 가변 행 2차원 배열 합계 ──
    static void d2() {
        // 행마다 길이가 다른 "가변(jagged) 배열"
        int[][] scores = {
            {10, 20},
            {30, 40, 50},
            {60}
        };

        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores[i].length; j++) { // 행마다 길이가 다르므로 scores[i].length 사용
                total += scores[i][j];
            }
        }
        System.out.println("합계: " + total); // 합계: 210
    }

    // ── D-3. 배열 복사 3방식 구현 및 비교 ──
    static void d3() {
        int[] origin = {1, 2, 3};

        // 방식 1: Arrays.copyOf — 길이 조절 가능, 가장 간편
        int[] copy1 = Arrays.copyOf(origin, origin.length);

        // 방식 2: clone — 자기 자신과 같은 길이의 복사본
        int[] copy2 = origin.clone();

        // 방식 3: System.arraycopy — 원본 일부를 대상 배열의 원하는 위치로 복사 (저수준, 세밀한 제어)
        int[] copy3 = new int[origin.length];
        System.arraycopy(origin, 0, copy3, 0, origin.length);

        // 복사본을 수정해도 원본은 영향 없음 (진짜 복사이므로)
        copy1[0] = 100;
        copy2[0] = 200;
        copy3[0] = 300;

        System.out.println(Arrays.toString(origin)); // [1, 2, 3]  <- 원본 그대로!
        System.out.println(Arrays.toString(copy1));  // [100, 2, 3]
        System.out.println(Arrays.toString(copy2));  // [200, 2, 3]
        System.out.println(Arrays.toString(copy3));  // [300, 2, 3]
    }
}
