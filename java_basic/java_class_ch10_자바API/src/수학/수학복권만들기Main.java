package 수학;

import java.util.Arrays;
import java.util.Random;

public class 수학복권만들기Main {
    public static void main(String[] args) {
        //중복없이
        Random random = new Random();
        int winningCount = 0;
        for (int j = 0; j < 80000000; j++) {
            int[] selectNumber = pickUniqueNumbers(random); //선택번호
            Arrays.sort(selectNumber);  //복권에서는 정렬된 값으로 비교
            //System.out.println("선택번호 : " + Arrays.toString(selectNumber));

            int[] winningNumber = pickUniqueNumbers(random);  //당첨번호
            Arrays.sort(winningNumber);
            //System.out.println("당첨번호 : " + Arrays.toString(winningNumber));

            boolean result = Arrays.equals(selectNumber, winningNumber);
            if (result) {
                winningCount++;
            }
        }
        System.out.println("8000만번했지만 당첨된 횟수는  " + winningCount +"구나");
    }

    //1~45 중에서 중복 없이 6개 뽑기
    public static int[] pickUniqueNumbers(Random random) {
        boolean[] used = new boolean[46]; //이미 뽑은 번호 표시용
        int[] numbers = new int[6];
        int count = 0;
        while (count < 6) {
            int num = random.nextInt(45) + 1;
            if (!used[num]) {  //아직 안 뽑힌 번호만 채택
                used[num] = true;
                numbers[count] = num;
                count++;
            }
        }
        return numbers;
    }

}
