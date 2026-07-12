package quest;

// D-1 ~ D-2. + 방식 vs StringBuilder 방식 + 시간 측정 비교
public class SolutionD {
    public static void main(String[] args) {
        int n = 100_000;

        // 방식 1: + 연결 (매 반복마다 새 String 생성)
        long start1 = System.nanoTime();
        String plusResult = "";
        for (int i = 1; i <= n; i++) {
            plusResult += i;
        }
        long time1 = System.nanoTime() - start1;

        // 방식 2: StringBuilder (내부 버퍼에 누적)
        long start2 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(i);
        }
        String sbResult = sb.toString();
        long time2 = System.nanoTime() - start2;

        System.out.println("결과 동일 여부: " + plusResult.equals(sbResult)); // 결과 동일 여부: true
        System.out.println("+ 방식        : " + time1 / 1_000_000 + " ms"); // 예: 4500 ms (실행 환경에 따라 다름)
        System.out.println("StringBuilder : " + time2 / 1_000_000 + " ms"); // 예: 3 ms (실행 환경에 따라 다름)
        System.out.println("결과 길이: " + sbResult.length());               // 결과 길이: 488895
    }
}
