class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Part1_ThreadExample {
    public static void main(String[] args) {
        System.out.println("=== Thread 클래스 상속 ===");
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();
        t1.start();
        t2.start();

        try {
            //sleep(600)으로 어림잡아 기다리면 타이밍에 따라 출력이 섞일 수 있다.
            //join()은 해당 스레드가 끝날 때까지 확실하게 기다린다.
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Runnable 인터페이스 ===");
        Thread t3 = new Thread(new MyRunnable());
        Thread t4 = new Thread(new MyRunnable());
        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== 람다식 ===");
        Thread t5 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("람다 스레드: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t5.start();

        try {
            t5.join();   //t5가 끝나기를 기다리지 않으면 "메인 스레드 종료"가 먼저 출력된다.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n메인 스레드 종료");
    }
}

