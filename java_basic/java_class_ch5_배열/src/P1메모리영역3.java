public class P1메모리영역3 {
    public static void main(String[] args) {
        //그 다음 참조 타입 변수 ==, != 연산
        int[] arr1={1,2,3};
        int[] arr2=arr1;
        int a=1;
        int b=a;


        b=10;
        arr2[0]=10;

        System.out.println(a);
        System.out.println(arr1[0]);


    }
}