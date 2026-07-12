package quest;

// E-1. 동기화 없는 카운터에서 race condition 재현
// 두 스레드가 같은 값을 동시에 읽고 각자 +1 해서 쓰면 증가 하나가 사라진다(lost update).
// 실행할 때마다 결과가 달라지는 것 자체가 race condition의 증거다. 해결은 D-1(synchronized) 또는 D-2(AtomicInteger).
public class SolutionE1 {
    static int count = 0; // 아무 보호 장치 없는 공유 변수

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100_000; i++) {
                count++; // 읽기 → +1 → 쓰기 (3단계, 원자적이지 않음!)
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("기대값: 200000, 실제: " + count);
        // 기대값: 200000, 실제: 137482  <- 실행마다 다르며, 거의 항상 200000보다 작다
    }
}
