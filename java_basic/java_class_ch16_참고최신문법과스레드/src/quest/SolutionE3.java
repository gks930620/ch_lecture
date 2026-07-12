package quest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// E-3. 타임아웃 있는 락 시도로 무한 대기 방지
// synchronized는 락을 잡을 때까지 무한정 기다리지만, ReentrantLock.tryLock(시간)은 제한 시간 후 false를 반환해
// 대안 로직(포기/재시도/에러 응답)으로 빠질 수 있다. unlock()은 반드시 finally에 둔다.
public class SolutionE3 {
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // t1이 락을 3초간 점유
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("t1: 락 획득, 3초간 작업");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock(); // 락 해제는 반드시 finally에서
            }
        });

        // t2는 최대 1초만 기다리고 포기
        Thread t2 = new Thread(() -> {
            try {
                if (lock.tryLock(1, TimeUnit.SECONDS)) { // 1초 안에 못 잡으면 false
                    try {
                        System.out.println("t2: 락 획득 성공");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("t2: 1초 대기 후 포기 — 무한 대기 없음");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        t1.start();
        Thread.sleep(100); // t1이 먼저 락을 잡도록 약간 대기
        t2.start();
        t1.join(); t2.join();
        // t1: 락 획득, 3초간 작업
        // t2: 1초 대기 후 포기 — 무한 대기 없음
    }
}
