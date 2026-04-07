package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Q4 {
    public static void main(String[] args) {
        String data = "hello\njava";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("입출력 오류: " + e.getMessage());
        }
    }
}
