package quest;

public class Q2 {
    interface Printable {
        void print();
    }

    interface Scannable {
        void scan();
    }

    static class MultiDevice implements Printable, Scannable {
        @Override
        public void print() {
            System.out.println("문서 출력");
        }

        @Override
        public void scan() {
            System.out.println("문서 스캔");
        }
    }

    public static void main(String[] args) {
        MultiDevice device = new MultiDevice();
        device.print();
        device.scan();
    }
}
