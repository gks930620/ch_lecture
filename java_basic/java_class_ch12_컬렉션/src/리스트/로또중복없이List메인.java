package 리스트;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class 로또중복없이List메인 {
    public static void main(String[] args) {
        // contains만 활용하면 됨.
        // 1~45 중 중복 없이 6개를 뽑는다. 이미 뽑은 번호면 List.contains로 걸러 다시 뽑는다.
        List<Integer> lotto = new ArrayList<>();
        Random random = new Random();

        while (lotto.size() < 6) {
            int num = random.nextInt(45) + 1;   // 1 ~ 45
            if (!lotto.contains(num)) {          // 아직 안 뽑힌 번호만 추가
                lotto.add(num);
            }
        }

        System.out.println("로또 번호: " + lotto);
    }
}
