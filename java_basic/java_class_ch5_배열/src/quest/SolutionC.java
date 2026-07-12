package quest;

// C. 배열 기초 (C-1 ~ C-4)
public class SolutionC {

    // C-4. 특정 값의 첫 인덱스 찾기 (없으면 -1)
    static int indexOf(int[] arr, int target) {
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i; // 찾는 즉시 반환 (조기 반환)
            }
        }
        return -1; // 끝까지 못 찾음
    }

    public static void main(String[] args) {
        // C-1. 최댓값/최솟값/합계/평균
        int[] arr = {5, 3, 9, 1, 7};
        int max = arr[0];
        int min = arr[0]; // 초기값은 0이 아니라 arr[0]
        int sum = 0;
        for (int v : arr) {
            if (v > max) max = v;
            if (v < min) min = v;
            sum += v;
        }
        double avg = (double) sum / arr.length;
        System.out.println("최댓값: " + max); // 9
        System.out.println("최솟값: " + min); // 1
        System.out.println("합계: " + sum);   // 25
        System.out.println("평균: " + avg);   // 5.0

        // C-2. 배열 역순 출력
        int[] arr2 = {10, 20, 30, 40, 50};
        for (int i = arr2.length - 1; i >= 0; i--) {
            System.out.println(arr2[i]); // 50 40 30 20 10
        }

        // C-3. 짝수/홀수 개수
        int[] arr3 = {3, 8, 2, 7, 5, 10, 4};
        int evenCount = 0;
        int oddCount = 0;
        for (int v : arr3) {
            if (v % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        System.out.println("짝수: " + evenCount + "개"); // 4개
        System.out.println("홀수: " + oddCount + "개");  // 3개

        // C-4
        int[] arr4 = {4, 7, 1, 7, 9};
        System.out.println(indexOf(arr4, 7));   // 1 (첫 번째 7의 위치)
        System.out.println(indexOf(arr4, 100)); // -1
    }
}
