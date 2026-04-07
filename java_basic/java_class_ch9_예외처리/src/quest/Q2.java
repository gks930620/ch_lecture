package quest;

import java.io.IOException;

public class Q2 {
    static void checkValue(int n) throws IOException {
        if (n < 0) {
            throw new IOException("음수는 허용되지 않습니다.");
        }
    }

    public static void main(String[] args) {
        try {
            checkValue(-1);
        } catch (IOException e) {
            System.out.println("처리된 예외: " + e.getMessage());
        }
    }
}
