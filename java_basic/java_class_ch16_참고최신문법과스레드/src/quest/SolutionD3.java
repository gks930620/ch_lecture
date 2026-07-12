package quest;

// D-3. volatile 플래그로 스레드 종료 제어
// volatile이 없으면 워커 스레드가 running을 캐시된 값으로만 읽어 무한 루프에 빠질 수 있다.
public class SolutionD3 {
    // volatile: 한 스레드의 쓰기가 다른 스레드에 "즉시 보이도록" 가시성 보장
    static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long loops = 0;
            while (running) { // volatile이 없으면 변경을 영영 못 볼 수도 있다
                loops++;
            }
            System.out.println("작업 스레드 종료 (반복 " + loops + "회)");
            // 반복 횟수는 실행마다 다를 수 있음
        });
        worker.start();

        Thread.sleep(100);   // 잠시 실행하게 둔 뒤
        running = false;     // main 스레드가 종료 신호를 보냄
        worker.join();

        System.out.println("main 종료");
    }
}
