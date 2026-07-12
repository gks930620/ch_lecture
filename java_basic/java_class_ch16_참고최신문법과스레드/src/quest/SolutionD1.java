package quest;

// D-1. 공유 카운터를 synchronized로 안전하게 증가
// synchronized 블록은 상호 배제(한 번에 한 스레드)를 보장해 읽기->+1->쓰기 3단계가 통째로 실행되게 만든다.
public class SolutionD1 {
    static int count = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 10_000; i++) {
                synchronized (lock) { // 한 번에 한 스레드만 이 블록에 진입
                    count++;
                }
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println(count); // 20000 (항상 정확)
    }
}
