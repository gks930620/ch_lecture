package quest;

/**
 * ch3 E. 대입/증감/삼항 — 정답 예시 (E-1 ~ E-4)
 */
public class SolutionE {
    public static void main(String[] args) {
        e1();
        e2();
        e3();
        e4();
    }

    // ── E-1. 전위/후위 증감 예제 5개 ──
    static void e1() {
        // 1) 전위: 먼저 증가시키고 그 값을 사용
        int i1 = 5;
        int a = ++i1;
        System.out.println(a + ", " + i1); // 6, 6

        // 2) 후위: 현재 값을 먼저 사용하고 나서 증가
        int i2 = 5;
        int b = i2++;
        System.out.println(b + ", " + i2); // 5, 6

        // 3) 출력식 안의 후위
        int i3 = 5;
        System.out.println(i3++); // 5
        System.out.println(i3);   // 6

        // 4) 출력식 안의 전위
        int i4 = 5;
        System.out.println(++i4); // 6

        // 5) 한 식에 섞인 경우 (왼쪽에서 오른쪽으로 평가)
        int i5 = 5;
        int c = i5++ + ++i5; // 5 + 7
        System.out.println(c + ", " + i5); // 12, 7
    }

    // ── E-2. s += 1은 되는데 s = s + 1이 안 되는 이유 ──
    static void e2() {
        short s = 1;
        s += 1;              // OK: s = (short)(s + 1) 로 컴파일됨
        // s = s + 1;        // 컴파일 오류: s + 1의 결과 타입은 int
        s = (short) (s + 1); // 명시적 캐스팅을 하면 통과
        System.out.println(s); // 3
    }

    // ── E-3. 중첩 삼항 연산자 → if-else 리팩터링 ──
    static void e3() {
        int score = 85;

        // 리팩터링 전: 읽기 어려운 중첩 삼항
        String grade1 = score >= 90 ? "A" : score >= 80 ? "B" : score >= 70 ? "C" : "F";

        // 리팩터링 후: if-else if
        String grade2;
        if (score >= 90) {
            grade2 = "A";
        } else if (score >= 80) {
            grade2 = "B";
        } else if (score >= 70) {
            grade2 = "C";
        } else {
            grade2 = "F";
        }

        System.out.println(grade1); // B
        System.out.println(grade2); // B
    }

    // ── E-4. 복합 대입 연산의 타입 변화 추적 ──
    static void e4() {
        // 1) byte += int : 내부에서 (byte) 캐스팅
        byte b = 10;
        b += 5; // b = (byte)(b + 5)
        System.out.println(b); // 15

        // 2) int *= double : 실수 곱셈 후 (int)로 절단!
        int n = 7;
        n *= 1.5; // n = (int)(7 * 1.5) = (int)10.5
        System.out.println(n); // 10

        // 3) char += int : 문자 코드 이동
        char c = 'A';
        c += 1; // c = (char)('A' + 1)
        System.out.println(c); // B

        // 4) byte 오버플로우가 조용히 발생
        byte big = 120;
        big += 10; // (byte)130 -> 래핑
        System.out.println(big); // -126
    }
}
