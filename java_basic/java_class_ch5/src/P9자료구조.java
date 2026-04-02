import java.util.Arrays;

public class P9자료구조 {
    public static void main(String[] args) {
        //정렬되지 않은 자료에서는 그냥 0~n까지 찾을 수 밖에..
        //정렬 (버블,선택,삽입) , 검색,
        //자료구조는 적어도 콜렉션 하고나서 해야될 듯 하다(List,map 등 쓰는게 편함). 정렬, 검색만 해보자.
        //정렬
        int[]  arr={3,1,5,9,7};
        insertSort(arr);
        System.out.println(Arrays.toString(arr));
        //정렬정도만 하자. 탐색 및 자료구조는 안함. 딱 정렬까지.
    }

    //오름차순
    public static void bubbleSort(int[]  arr){  //return이 없고 매개변수로 받은 값의 내용을 변경
        for(int i=0 ; i<arr.length ; i++){
            for(int j=i ; j<arr.length-i-1 ; j++){
                if(arr[j] > arr[j+1])switchElement(arr,j,j+1);
            }
        }
    }
    public static void sellectionSort(int[] arr){
        //가장큰값을 뒤로 or 가장 작은 값을 앞으로
        //이건 가장 작은 값을 앞으로
        for(int i=0 ; i<arr.length ; i++){
            int curMin=Integer.MAX_VALUE;
            int minIndex=i;
            for(int j=i ; j<arr.length ; j++){
                if(curMin>arr[j]){
                    curMin=arr[j];
                    minIndex=j;
                }
            }
            switchElement(arr,i,minIndex);  //i가 최소값일때는 뭐 자기랑 자기 값 바꾸긴 함.
        }

    }
    public  static void insertSort(int[] arr){
        // arr[i]값을  i-1 ~ 0  (j) 원소의 값들이랑 비교.
        // arr[i]값이   arr[j]보다 작으면   한칸 땡김 ( arr[j], arr[j+1]이랑 바꿈)
        //  arr[i]값이  arr[j]보다 크면(같아도) 멈춤.  arr[j+1]의 값은 cur로 바꿈
        for(int i=1 ;  i< arr.length ; i++){
            int cur=arr[i];
            for(int j=i-1 ; j>=0 ; j--){
                if(cur>=arr[j]){
                    arr[j+1]=cur;
                    break;
                }
                if(cur < arr[j])switchElement(arr,j,j+1);
            }
        }
    }

    public static  void switchElement(int[] arr, int a, int b){
        int temp=arr[a];
        arr[a]=arr[b];
        arr[b]=temp;
    }



}
