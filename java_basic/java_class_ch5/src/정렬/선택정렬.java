package 정렬;

import java.util.Arrays;

public class 선택정렬 {

    public static void main(String[] args) {
        int[] arr = {11, 12, 45, 4, 34, 2, 50, 5, 29, 49};    // 최소값 2는  5번째 인덱스에 있음
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }



    //가장 작은 값을 찾아서  0번부터
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {    // 배열에서
            int minValue = arr[i];    //  일단 젤 왼쪽게  가장 작은값이라고 가정
            int minIndex=i;
            for(int j=i+1 ; j<arr.length ; j++){
                if(arr[j] < minValue   ) {
                    minValue = arr[j];  //  더 작은값이 나오면 그걸로 업데이트
                    minIndex=j;  //인덱스도 업데이트
                }
            }
            //  첫번째 행위에서는   최소값 2인   index 5 를 찾을 수 있음.     이걸 0번째 인덱스로 옮김
            int temp=arr[i];
            arr[i]=arr[minIndex];
            arr[minIndex]=temp;
        }
    }
}
