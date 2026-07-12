package quest;

// A-3. NumberFormatException 처리
public class SolutionA3 {
    public static void main(String[] args) {
        String input = "12a"; // 잘못된 입력 가정
        try {
            int number = Integer.parseInt(input);
            System.out.println("입력한 숫자: " + number);
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 아닙니다. 다시 입력해 주세요: " + input);
        }
    }
}
// 출력:
// 숫자 형식이 아닙니다. 다시 입력해 주세요: 12a
