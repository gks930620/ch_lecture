package quest;

// C-1. 앞뒤 공백 제거 + 소문자 정규화
public class SolutionC1 {
    public static void main(String[] args) {
        String input = "  Hello Java  ";
        String normalized = input == null ? "" : input.strip().toLowerCase();

        System.out.println("[" + normalized + "]"); // [hello java]
    }
}
