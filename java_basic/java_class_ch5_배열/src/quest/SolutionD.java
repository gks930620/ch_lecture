package quest;

import java.util.Arrays;

// D. 배열 응용 (D-1 ~ D-4)
public class SolutionD {

    public static void main(String[] args) {
        // D-1. 1~45 중복 없는 로또 번호 6개
        int[] lotto = new int[6];
        int count = 0;
        while (count < 6) {
            int num = (int) (Math.random() * 45) + 1;
            boolean duplicated = false;
            for (int i = 0; i < count; i++) { // 지금까지 채워진 count개와만 비교
                if (lotto[i] == num) {
                    duplicated = true;
                    break;
                }
            }
            if (!duplicated) {
                lotto[count] = num;
                count++;
            }
        }
        System.out.println(Arrays.toString(lotto)); // 실행마다 다름, 중복 없음

        // D-2. 2차원 배열의 행별 합계와 전체 합계
        int[][] table = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int total = 0;
        for (int i = 0; i < table.length; i++) {
            int rowSum = 0; // 행마다 0으로 리셋
            for (int j = 0; j < table[i].length; j++) {
                rowSum += table[i][j];
            }
            System.out.println(i + "행 합계: " + rowSum);
            total += rowSum;
        }
        System.out.println("전체 합계: " + total); // 21

        // D-3. 가변 행(jagged) 2차원 배열 순회
        int[][] jagged = {
            {1, 2},
            {3, 4, 5},
            {6}
        };
        for (int i = 0; i < jagged.length; i++) {
            for (int j = 0; j < jagged[i].length; j++) { // 행마다 길이가 다름
                System.out.print(jagged[i][j] + " ");
            }
            System.out.println();
        }

        // D-4. 정렬 후 이진 탐색
        int[] arr = {9, 3, 7, 1, 5};
        Arrays.sort(arr); // 이진 탐색의 전제: 정렬
        System.out.println(Arrays.toString(arr)); // [1, 3, 5, 7, 9]
        int idx = Arrays.binarySearch(arr, 7);
        System.out.println("7의 인덱스: " + idx); // 3
        int notFound = Arrays.binarySearch(arr, 4);
        System.out.println("4 검색 결과: " + notFound); // 음수 (없음)
    }
}
