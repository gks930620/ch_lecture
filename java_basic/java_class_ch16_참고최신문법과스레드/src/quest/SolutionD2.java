package quest;

import java.util.concurrent.atomic.AtomicInteger;

// D-2. 동일 기능을 AtomicInteger로 구현
// incrementAndGet()은 CPU의 CAS(compare-and-swap) 명령으로 증가 전체를 원자적으로 수행한다. 락 없이 가볍고 빠르다.
public class SolutionD2 {
    static final AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                count.incrementAndGet(); // 하드웨어 수준의 원자 연산 (락 없음)
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println(count.get()); // 20000 (항상 정확)
    }
}
