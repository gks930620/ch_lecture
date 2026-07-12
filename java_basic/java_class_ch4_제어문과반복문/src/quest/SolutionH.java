package quest;

/**
 * ch4 H. 디버깅 문제 — 정답(수정 코드) 예시
 *
 * 버그 원인
 *  1) 경계 조건(오프바이원): i <= arr.length → i < arr.length 로 수정
 *     (arr[arr.length]는 ArrayIndexOutOfBoundsException)
 *  2) 중괄호 생략 위험: if 블록에 중괄호를 명시
 */
public class SolutionH {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};

        for (int i = 0; i < arr.length; i++) { // 수정 1: <= -> <
            if (arr[i] % 2 == 0) {             // 수정 2: 중괄호 명시
                continue;
            }
            System.out.println(arr[i]);
        }
        // 1
        // 3
        // 5
    }
}
