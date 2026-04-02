package 정렬;

import java.util.Arrays;

public class 선택정렬2 {

    // 구현은 기능정의에 맞춰서 구현하는게 좋음
    // 시간 :  60초는 1분, 60분은 1시간 ,     1시간은  몇 초  ?  ==>    3600초!!! 가 아니라 60 X 60 초!
    // 윤년 :  boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    //           보다는 if return 방식으로



    public static void main(String[] args) {
        int[] arr = {11, 12, 45, 4, 34, 2, 50, 5, 29, 49};    // 최소값 2는  5번째 인덱스에 있음
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }
    //가장 작은 값을 찾아서  0번부터
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = findMinIndex(arr, i);
            changeValue(arr, i, minIndex);
        }
    }
   //이렇게 하면 복잡하게 생각하지말고,  각 메소드의 정의에 맞는 기능만 구현하면 됨.



    //혹시 어렵다면... 2번째 파라미터 빼고 ,  배열에서 최소값 인덱스만 ... => 0을 i로만 바꾸면 됨
    //배열의 i번째부터 끝까지 중  최소값의 인덱스를 구하는 메소드
    private static int findMinIndex(int[] arr, int i) {
        int minValue = arr[i];    //  일단 젤 왼쪽게  가장 작은값이라고 가정
        int minIndex= i;
        for(int j= i +1 ; j< arr.length ; j++){
            if(arr[j] < minValue   ) {
                minValue = arr[j];  //  더 작은값이 나오면 그걸로 업데이트
                minIndex=j;  //인덱스도 업데이트
            }
        }
        return minIndex;
    }


    // 배열에서 두 인덱스의 값을 서로 바꾸는 메소드
    private static void changeValue(int[] arr, int index1, int index2) {
        int temp= arr[index1];
        arr[index1]= arr[index2];
        arr[index2]=temp;
    }


}
