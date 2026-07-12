package quest;

/**
 * ch3 F. 디버깅 문제 — 정답(수정 코드) 예시
 *
 * 버그 원인
 *  1) 문자열 비교를 ==로 수행 → equals로 수정 (상수를 앞에 두면 null 안전)
 *  2) 정수 오버플로우 → 큰 누적 합계는 long으로 수정
 */
public class SolutionF {
    public static void main(String[] args) {
        String role = new String("ADMIN");
        if ("ADMIN".equals(role)) {   // 수정 1: equals로 내용 비교
            System.out.println("관리자"); // 관리자
        }

        long total = 0L;              // 수정 2: long으로 누적
        for (int i = 1; i <= 1000000; i++) {
            total += i;
        }
        System.out.println(total);    // 500000500000
    }
}
