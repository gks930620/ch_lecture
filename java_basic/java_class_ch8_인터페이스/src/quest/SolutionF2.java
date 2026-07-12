package quest;

// F-2. 익명 클래스 vs 람다로 Runnable 구현 비교
public class SolutionF2 {
    public static void main(String[] args) {
        // 방법 1: 익명 클래스 — 이름 없는 일회용 구현 클래스를 그 자리에서 정의
        Runnable anonymous = new Runnable() {
            @Override
            public void run() {
                System.out.println("익명 클래스로 실행");
            }
        };

        // 방법 2: 람다 — Runnable은 추상 메소드(run)가 하나뿐이므로 가능
        Runnable lambda = () -> System.out.println("람다로 실행");

        anonymous.run(); // 익명 클래스로 실행
        lambda.run();    // 람다로 실행
    }
}
