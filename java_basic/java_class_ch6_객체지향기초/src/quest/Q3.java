package quest;

public class Q3 {
    static class Counter {
        static int count = 0;

        Counter() {
            count++;
        }
    }

    public static void main(String[] args) {
        new Counter();
        new Counter();
        new Counter();
        System.out.println("생성된 객체 수: " + Counter.count);
    }
}
