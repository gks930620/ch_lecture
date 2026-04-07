package quest;

public class Q4 {
    static class Singleton {
        private static final Singleton INSTANCE = new Singleton();

        private Singleton() {
        }

        static Singleton getInstance() {
            return INSTANCE;
        }
    }

    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println("같은 인스턴스 여부: " + (s1 == s2));
    }
}
