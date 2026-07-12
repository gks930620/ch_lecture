package quest;

// A-4. finally 블록이 항상 실행되는지 확인
public class SolutionA4 {
    public static void main(String[] args) {
        System.out.println("--- 예외가 없는 경우 ---");
        run("100");
        System.out.println("--- 예외가 있는 경우 ---");
        run("abc");
    }

    static void run(String input) {
        try {
            int n = Integer.parseInt(input);
            System.out.println("변환 성공: " + n);
        } catch (NumberFormatException e) {
            System.out.println("변환 실패");
        } finally {
            System.out.println("finally 실행");
        }
    }
}
// 출력:
// --- 예외가 없는 경우 ---
// 변환 성공: 100
// finally 실행
// --- 예외가 있는 경우 ---
// 변환 실패
// finally 실행
