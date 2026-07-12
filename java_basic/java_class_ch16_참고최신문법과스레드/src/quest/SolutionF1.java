package quest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// F-1. 주문 처리 큐 (Producer + Consumer 스레드풀 + 재시도/로그)
// BlockingQueue가 생산자-소비자 사이의 완충 지대 역할을 하고, put/poll이 대기를 알아서 처리한다.
// 실패를 "재시도 가능/불가"로 구분하고 재시도 횟수를 주문 데이터에 담아 무한 재시도를 막는 것이 실무 패턴이다.
public class SolutionF1 {

    // F-2. 최신 문법 적용 1: 주문 데이터는 record로 (불변 + toString 자동)
    record Order(int id, String item, int retryCount) {
        Order retry() { return new Order(id, item, retryCount + 1); }
    }

    enum Result { SUCCESS, RETRYABLE_FAIL, FATAL_FAIL }

    static final int ORDER_COUNT = 20;
    static final int MAX_RETRY = 2;

    // F-3. 메트릭 수집용 카운터
    static final AtomicInteger successCount = new AtomicInteger();
    static final AtomicInteger failCount = new AtomicInteger();
    static final AtomicLong totalLatencyMs = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Order> queue = new LinkedBlockingQueue<>();
        ExecutorService consumers = Executors.newFixedThreadPool(3);

        // Producer: 주문 생성 스레드
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= ORDER_COUNT; i++) {
                try {
                    queue.put(new Order(i, "상품-" + i, 0)); // 가득 차면 자리 날 때까지 대기
                    System.out.println("[생산] 주문 " + i + " 접수");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });
        producer.start();
        producer.join(); // 데모 단순화: 생산 완료 후 소비 시작 상태 확정

        // Consumer: 스레드풀에서 큐를 비울 때까지 처리
        while (!queue.isEmpty()) {
            Order order = queue.poll();
            if (order == null) break;
            consumers.submit(() -> process(order, queue));
        }
        // 재시도 주문이 큐에 다시 들어올 수 있으므로 잠시 후 잔여분 처리
        consumers.shutdown();
        consumers.awaitTermination(10, TimeUnit.SECONDS);
        drainRetries(queue); // 남은 재시도 주문을 현재 스레드에서 마저 처리

        printMetrics(); // F-3
    }

    static void process(Order order, BlockingQueue<Order> queue) {
        long start = System.currentTimeMillis();
        Result result = doWork(order);
        totalLatencyMs.addAndGet(System.currentTimeMillis() - start);

        // F-2. 최신 문법 적용 2: 처리 결과 분기는 switch 표현식으로
        String log = switch (result) {
            case SUCCESS -> {
                successCount.incrementAndGet();
                yield "[성공] " + order;
            }
            case RETRYABLE_FAIL -> {
                if (order.retryCount() < MAX_RETRY) {
                    queue.offer(order.retry()); // 재시도 큐 재투입
                    yield "[재시도 예약] " + order;
                }
                failCount.incrementAndGet();
                yield "[최종 실패] 재시도 초과: " + order;
            }
            case FATAL_FAIL -> {
                failCount.incrementAndGet();
                yield "[최종 실패] 복구 불가: " + order;
            }
        };
        System.out.println(log);
    }

    /** 실제 처리를 흉내: 80% 성공, 15% 재시도 가능 실패, 5% 복구 불가 실패 */
    static Result doWork(Order order) {
        sleep(ThreadLocalRandom.current().nextInt(10, 50)); // 처리 지연 흉내
        int dice = ThreadLocalRandom.current().nextInt(100);
        if (dice < 80) return Result.SUCCESS;
        if (dice < 95) return Result.RETRYABLE_FAIL;
        return Result.FATAL_FAIL;
    }

    static void drainRetries(BlockingQueue<Order> queue) {
        Order order;
        while ((order = queue.poll()) != null) {
            process(order, queue);
        }
    }

    // F-3. 처리량/실패율/평균지연 메트릭 출력
    static void printMetrics() {
        int total = successCount.get() + failCount.get();
        System.out.println("=== 메트릭 ===");
        System.out.println("처리량: " + total + "건 (성공 " + successCount.get()
                + " / 실패 " + failCount.get() + ")");
        System.out.printf("실패율: %.1f%%%n", failCount.get() * 100.0 / Math.max(total, 1));
        System.out.printf("평균 지연: %.1fms%n", (double) totalLatencyMs.get() / Math.max(total, 1));
        // 예시 출력 (수치는 실행마다 다를 수 있음):
        // === 메트릭 ===
        // 처리량: 20건 (성공 19 / 실패 1)
        // 실패율: 5.0%
        // 평균 지연: 29.3ms
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
