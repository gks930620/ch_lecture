package quest;

// C-3. CSV 한 줄 split
public class SolutionC3 {
    public static void main(String[] args) {
        String line = "1001,김자바,kim@test.com,28";
        String[] fields = line.split(",");

        for (int i = 0; i < fields.length; i++) {
            System.out.println("필드[" + i + "] = " + fields[i]);
        }
    }
}
// 출력:
// 필드[0] = 1001
// 필드[1] = 김자바
// 필드[2] = kim@test.com
// 필드[3] = 28
