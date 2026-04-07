package 수학;

import java.util.Arrays;
import java.util.Random;

public class 수학복권만들기Main {
    public static void main(String[] args) {
        //중복없이
        Random random = new Random();
        int[] selectNumber = new int[6]; //선택번호
        int[] winningNumber = new int[6];  //당첨번호
        int winningCount = 0;
        for (int j = 0; j < 80000000; j++) {
            for (int i = 0; i < selectNumber.length; i++) {
                selectNumber[i] = random.nextInt(45) + 1;
            }
            Arrays.sort(selectNumber);  //복권에서는 정렬된 값으로 비교
            //System.out.println("선택번호 : " + Arrays.toString(selectNumber));

            for (int i = 0; i < winningNumber.length; i++) {
                winningNumber[i] = random.nextInt(45) + 1;
            }
            Arrays.sort(winningNumber);
            //System.out.println("당첨번호 : " + Arrays.toString(winningNumber));

            boolean result = Arrays.equals(selectNumber, winningNumber);
            if (result) {
                winningCount++;
            }
        }
        System.out.println("8000만번했지만 당첨된 횟수는  " + winningCount +"구나");
    }
    
}
