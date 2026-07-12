package quest;

/**
 * ch2 A. 기초 확인 — 정답 예시
 *
 * A-1 ~ A-4는 설명형 문제라 코드가 없고,
 * A-5(배열이 참조 자료형임을 보여주는 예제)만 실행 코드로 옮겼습니다.
 */
public class SolutionA {
    public static void main(String[] args) {
        a5();
    }

    // ── A-5. 배열이 참조 자료형임을 보여주는 예제 ──
    static void a5() {
        int[] a = {1, 2, 3};
        int[] b = a;              // 배열이 복사되는 게 아니라 "같은 배열의 주소"가 복사됨

        b[0] = 99;                // b를 통해 값 변경

        System.out.println(a[0]); // 99  <- a도 바뀐 것처럼 보임 (사실 같은 배열)
        System.out.println(b[0]); // 99
        System.out.println(a == b); // true (같은 객체를 가리킴)
    }
}
