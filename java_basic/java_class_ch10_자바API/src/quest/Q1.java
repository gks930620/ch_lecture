package quest;

public class Q1 {
    public static void main(String[] args) {
        String text = "level";
        String reversed = new StringBuilder(text).reverse().toString();
        boolean isPalindrome = text.equals(reversed);

        System.out.println("원본: " + text);
        System.out.println("뒤집은 문자열: " + reversed);
        System.out.println("회문 여부: " + isPalindrome);
    }
}
