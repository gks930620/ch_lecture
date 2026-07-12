package quest;

/**
 * ch2 E. 상수/리터럴/var — 정답 예시
 *
 * E-1 static final 상수, E-2 진법 리터럴, E-3 var 사례를 실행 코드로 옮겼습니다.
 * E-4(final 참조 변수)는 설명형이라 생략합니다.
 */
public class SolutionE {
    // ── E-1. static final 상수 5개 선언 ──
    public static final int MAX_RETRY = 3;                    // 최대 재시도 횟수
    public static final int TIMEOUT_SECONDS = 30;             // 요청 제한 시간(초)
    public static final double TAX_RATE = 0.1;                // 부가세율 10%
    public static final long MAX_UPLOAD_SIZE = 10_485_760L;   // 업로드 제한 10MB (byte)
    public static final String APP_NAME = "MyShop";           // 애플리케이션 이름

    public static void main(String[] args) {
        System.out.println(APP_NAME + " 최대 재시도: " + MAX_RETRY);
        // 출력: MyShop 최대 재시도: 3
        // MAX_RETRY = 5; // 컴파일 오류: final 변수 재대입 불가

        e2();
        e3();
    }

    // ── E-2. 같은 수를 2/8/10/16진으로 표현 ──
    static void e2() {
        int binary  = 0b11010; // 2진수  (접두사 0b)
        int octal   = 032;     // 8진수  (접두사 0)
        int decimal = 26;      // 10진수
        int hex     = 0x1A;    // 16진수 (접두사 0x)

        System.out.println(binary);  // 26
        System.out.println(octal);   // 26
        System.out.println(decimal); // 26
        System.out.println(hex);     // 26
    }

    // ── E-3. var 가독성 좋은/나쁜 사례 ──
    static void e3() {
        // [좋은 사례] 우변만 봐도 타입이 명확 — 중복 제거 효과
        var message = "hello java";           // 누가 봐도 String
        var builder = new StringBuilder();    // 우변에 타입 이름이 그대로 보임
        var maxRetry = 3;                     // 누가 봐도 int

        // [나쁜 사례] 우변만 봐서는 타입을 알 수 없음
        var result = calculate();     // 반환 타입이 뭐지? int? double? String?
        var data = getData();         // 메소드 이름만으로 타입을 알 수 없음

        System.out.println(message);          // hello java
        System.out.println(result);           // 42
    }

    static int calculate() { return 42; }
    static String getData() { return "data"; }
}
