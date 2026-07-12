package quest;

// A-2. 배열 인덱스 범위 초과 예외 재현/처리
public class SolutionA2 {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30};
        try {
            System.out.println(arr[3]); // 유효 인덱스는 0~2
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("잘못된 인덱스 접근: " + e.getMessage());
        }
        System.out.println("배열 길이: " + arr.length);
    }
}
// 출력:
// 잘못된 인덱스 접근: Index 3 out of bounds for length 3
// 배열 길이: 3
