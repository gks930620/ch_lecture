package quest;

import java.util.Arrays;

// F. 챌린지 (F-1 ~ F-3)
public class SolutionF {

    // F-1. 성적 배열 -> 등급 분포(A/B/C/D/F). 반환: [A,B,C,D,F 개수]
    static int[] gradeDistribution(int[] scores) {
        if (scores == null) {
            throw new IllegalArgumentException("scores must not be null");
        }
        int[] dist = new int[5];
        for (int score : scores) {
            if (score >= 90) {
                dist[0]++;
            } else if (score >= 80) {
                dist[1]++;
            } else if (score >= 70) {
                dist[2]++;
            } else if (score >= 60) {
                dist[3]++;
            } else {
                dist[4]++;
            }
        }
        return dist;
    }

    // F-2. 회문(palindrome) 검사 — 투 포인터
    static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        char[] chars = s.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) { // 양끝에서 가운데로 좁혀오며 비교
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // F-3. 행렬 덧셈 (차원 검증 포함)
    static int[][] addMatrix(int[][] a, int[][] b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("행렬은 null일 수 없습니다");
        }
        if (a.length != b.length) {
            throw new IllegalArgumentException("행 수가 다릅니다");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i].length != b[i].length) {
                throw new IllegalArgumentException(i + "행의 열 수가 다릅니다");
            }
        }
        int[][] result = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new int[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // F-1
        int[] scores = {95, 82, 77, 64, 58, 91, 45};
        int[] dist = gradeDistribution(scores);
        String[] labels = {"A", "B", "C", "D", "F"};
        for (int i = 0; i < dist.length; i++) {
            System.out.println(labels[i] + ": " + dist[i] + "명");
        }

        // F-2
        System.out.println(isPalindrome("level")); // true
        System.out.println(isPalindrome("noon"));  // true
        System.out.println(isPalindrome("java"));  // false
        System.out.println(isPalindrome("a"));     // true

        // F-3
        int[][] a = {
            {1, 2},
            {3, 4}
        };
        int[][] b = {
            {5, 6},
            {7, 8}
        };
        int[][] sum = addMatrix(a, b);
        System.out.println(Arrays.deepToString(sum)); // [[6, 8], [10, 12]]
        try {
            addMatrix(a, new int[][]{{1, 2, 3}, {4, 5, 6}});
        } catch (IllegalArgumentException e) {
            System.out.println("오류: " + e.getMessage()); // 오류: 0행의 열 수가 다릅니다
        }
    }
}
