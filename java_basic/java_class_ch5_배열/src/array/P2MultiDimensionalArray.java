package array;

public class P2MultiDimensionalArray {
    public static void main(String[] args) {
        System.out.println("=== 2차원 배열 ===");

        // 2차원 배열 선언과 초기화
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        // 2차원 배열 출력
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // 반별 학생 점수
        System.out.println("\n=== 반별 학생 점수 ===");
        int[][] classScores = {
            {90, 85, 88, 92},  // 1반
            {78, 82, 85, 80},  // 2반
            {95, 92, 88, 90}   // 3반
        };

        for (int i = 0; i < classScores.length; i++) {
            System.out.print((i + 1) + "반: ");
            int sum = 0;
            for (int j = 0; j < classScores[i].length; j++) {
                System.out.print(classScores[i][j] + " ");
                sum += classScores[i][j];
            }
            double avg = (double) sum / classScores[i].length;
            System.out.println("(평균: " + avg + ")");
        }

        // 가변 배열
        System.out.println("\n=== 가변 배열 ===");
        int[][] jagged = {
            {1, 2},
            {3, 4, 5},
            {6, 7, 8, 9}
        };

        for (int i = 0; i < jagged.length; i++) {
            for (int j = 0; j < jagged[i].length; j++) {
                System.out.print(jagged[i][j] + " ");
            }
            System.out.println();
        }
    }
}

