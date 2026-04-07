package quest;

public class Q3 {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
            }
        };

        Thread t1 = new Thread(task, "worker-1");
        Thread t2 = new Thread(task, "worker-2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("모든 스레드 종료");
    }
}
