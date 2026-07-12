package quest;

/**
 * ch2 C. 형변환 집중 — 정답 예시 (C-1 ~ C-4)
 */
public class SolutionC {
    public static void main(String[] args) {
        c1();
        c2();
        c3();
        c4();
    }

    // ── C-1. 축소 형변환 손실 예제 ──
    static void c1() {
        // 1) long -> int: 32bit를 넘는 비트가 잘려나감
        long big = 3_000_000_000L;
        int i = (int) big;
        System.out.println(i);      // -1294967296 (int 범위 초과로 값이 깨짐)

        // 2) double -> int: 소수부를 버림 (반올림 아님!)
        double pi = 3.99;
        int n = (int) pi;
        System.out.println(n);      // 3

        // 3) int -> byte: 하위 8bit만 남음
        int value = 130;
        byte b = (byte) value;
        System.out.println(b);      // -126 (130은 byte 최댓값 127을 넘어 래핑됨)
    }

    // ── C-2. char 코드값/다음 문자/소문자 변환 ──
    static void c2() {
        char ch = 'A';

        System.out.println((int) ch);              // 65  ('A'의 유니코드 코드값)
        System.out.println((char) (ch + 1));       // B   (코드값 66 -> 문자로 변환)
        System.out.println((char) (ch + 32));      // a   (대문자 + 32 = 소문자)
        System.out.println(Character.toLowerCase(ch)); // a (표준 API 사용 — 실무 권장)
    }

    // ── C-3. 정수 나눗셈 vs 실수 나눗셈 ──
    static void c3() {
        int a = 5;
        int b = 2;

        System.out.println(a / b);            // 2    (int / int -> int, 소수부 버림)
        System.out.println(a / (double) b);   // 2.5  (한쪽이 double이면 실수 나눗셈)
        System.out.println(5.0 / 2);          // 2.5  (double 리터럴 사용)

        double wrong = a / b;   // 나눗셈이 먼저 int로 계산된 뒤 대입됨
        System.out.println(wrong);            // 2.0  (2.5가 아님! 흔한 실수)
    }

    // ── C-4. 언박싱 NPE 재현 + 방어 코드 ──
    static void c4() {
        Integer boxed = null;

        // int n = boxed; // 이 줄의 주석을 풀면 NullPointerException!
        //                // null인 Integer를 int로 자동 언박싱하는 순간 터진다

        // 방어 코드: 언박싱 전에 null 검사
        int safe;
        if (boxed != null) {
            safe = boxed;   // null이 아닐 때만 언박싱
        } else {
            safe = 0;       // 기본값 사용
        }
        System.out.println(safe); // 0
    }
}
