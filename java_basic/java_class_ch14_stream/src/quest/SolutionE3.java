package quest;

import java.util.stream.LongStream;

// E-3. 병렬 스트림과 일반 스트림 시간 측정
// 같은 계산을 순차/병렬로 수행하며 System.nanoTime()으로 비교한다.
// 데이터가 작거나 연산이 가벼우면 스레드 분배 비용 때문에 병렬이 오히려 느릴 수 있다.
public class SolutionE3 {
    public static void main(String[] args) {
        long n = 100_000_000L;

        // 순차 스트림
        long t1 = System.nanoTime();
        long seqSum = LongStream.rangeClosed(1, n).sum();
        long seqMs = (System.nanoTime() - t1) / 1_000_000;

        // 병렬 스트림
        long t2 = System.nanoTime();
        long parSum = LongStream.rangeClosed(1, n).parallel().sum();
        long parMs = (System.nanoTime() - t2) / 1_000_000;

        System.out.println("순차 합계: " + seqSum + " (" + seqMs + "ms)");
        System.out.println("병렬 합계: " + parSum + " (" + parMs + "ms)");
        System.out.println("결과 동일? " + (seqSum == parSum)); // true

        // 주의: 측정 시간은 CPU 코어 수, JIT 워밍업, 데이터 크기 등
        //       실행 환경에 따라 매번 다르다. 병렬이 항상 빠른 것도 아니다.
    }
}
