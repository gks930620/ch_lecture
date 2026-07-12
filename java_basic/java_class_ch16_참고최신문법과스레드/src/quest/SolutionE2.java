package quest;

// E-2. 락 순서가 엇갈리는 데드락 재현과 해결
// 데드락의 성립 조건 중 "순환 대기"를 끊는 가장 실용적인 방법이 전역 락 순서 통일이다.
// (아래 main은 안전한 "해결" 버전을 실행한다. deadlockScenario()는 참고용 — 호출하면 영원히 멈출 수 있다.)
public class SolutionE2 {
    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 해결: 두 스레드 모두 항상 A -> B 순서로 잡으면 데드락이 사라진다.
        fixedScenario();

        // 데드락을 직접 재현하려면 아래 주석을 해제한다 (강제 종료 필요!).
        // deadlockScenario();
    }

    // 해결 버전: t1, t2 모두 A -> B 순서로 락을 획득 → 순환 대기가 없어 항상 정상 종료
    static void fixedScenario() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("t1: A 획득");
                sleep(100);
                synchronized (lockB) {
                    System.out.println("t1: B 획득");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockA) {               // t1과 동일하게 A부터!
                System.out.println("t2: A 획득");
                sleep(100);
                synchronized (lockB) {
                    System.out.println("t2: B 획득");
                }
            }
        });

        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("정상 종료 (데드락 없음)");
    }

    // 데드락 재현 버전: t1은 A->B, t2는 B->A 순서라 서로가 가진 락을 기다리며 영원히 정지
    static void deadlockScenario() {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {               // 1) A를 잡고
                System.out.println("t1: A 획득");
                sleep(100);
                synchronized (lockB) {           // 2) B를 기다림 (t2가 쥐고 있음)
                    System.out.println("t1: B 획득");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {               // 1) B를 잡고
                System.out.println("t2: B 획득");
                sleep(100);
                synchronized (lockA) {           // 2) A를 기다림 (t1이 쥐고 있음)
                    System.out.println("t2: A 획득");
                }
            }
        });

        t1.start(); t2.start();
        // 이후 서로가 가진 락을 기다리며 영원히 정지 (데드락)
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
