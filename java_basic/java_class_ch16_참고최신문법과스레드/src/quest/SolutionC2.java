package quest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// C-2. ExecutorService 고정 스레드 풀로 작업 10개 실행
// 작업 10개를 위해 스레드 10개를 만들지 않고, 3개의 스레드가 큐에서 작업을 꺼내 재사용한다.
public class SolutionC2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3); // 스레드 3개로 고정

        for (int i = 1; i <= 10; i++) {
            final int taskNo = i; // 람다에서 쓰려면 사실상 final이어야 함
            pool.submit(() -> {
                System.out.println("작업 " + taskNo + " 처리: "
                        + Thread.currentThread().getName());
            });
        }

        pool.shutdown(); // 새 작업 접수 중단 (이미 제출된 작업은 계속 실행)
        pool.awaitTermination(10, TimeUnit.SECONDS); // 종료 대기

        System.out.println("풀 종료");
        // 작업 10개가 스레드 3개에 분배됨 (순서/담당 스레드는 실행마다 다를 수 있음)
    }
}
