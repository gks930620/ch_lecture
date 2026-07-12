package quest;

import java.util.regex.Pattern;

public class Q4 {
    public static void main(String[] args) {
        String email = "student@example.com";
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; //ch10 문서 7절과 동일한 패턴
        boolean valid = Pattern.matches(regex, email);
        System.out.println("이메일: " + email);
        System.out.println("유효 여부: " + valid);
    }
}
