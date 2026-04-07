package quest;

import java.util.Arrays;
import java.util.Random;

public class Q4 {
    public static void main(String[] args) {
        Random random = new Random();
        int[] lotto = new int[6];
        int index = 0;

        while (index < 6) {
            int n = random.nextInt(45) + 1;
            boolean exists = false;
            for (int i = 0; i < index; i++) {
                if (lotto[i] == n) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                lotto[index++] = n;
            }
        }

        Arrays.sort(lotto);
        System.out.println("로또 번호: " + Arrays.toString(lotto));
    }
}
