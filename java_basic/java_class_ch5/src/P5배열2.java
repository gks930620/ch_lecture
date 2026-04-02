import java.util.Arrays;

public class P5배열2 {
    public static void main(String[] args) {
        //다차원 배열.  행렬X,  N차원 확장으로 설명하기
        // 5명의 학생,    수학,과학,영어 점수
        //    5X 2 배열  2차원 배열
        int[][] studentScores=new int[5][3];
        for(int i=0 ; i<studentScores.length  ; i++){
            for(int j=0; j<studentScores[0].length ; j++){
                studentScores[i][j]=(int)(Math.random()*100);
            }
        }

        for(int i=0 ; i<studentScores.length  ; i++){
            System.out.println("학생"+i+"의 점수 : " +Arrays.toString(studentScores[i]));
        }

        //5명의 영어점수의 합, 평균.  .. 이걸 왜 못하지?...
        int engSum=0;
        for(int i=0 ; i<studentScores.length  ; i++){
            engSum+=studentScores[i][2];
        }
        System.out.println(engSum);



    }
}
