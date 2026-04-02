public class P1메모리영역2 {
    public static void main(String[] args) {
        //그 다음 참조 타입 변수 ==, != 연산
        int[] arr1=new int[]{1,2,3};
        int[] arr2=new int[]{1,2,3};
        int[] arr3=arr2;
        System.out.println(arr1==arr2);
        System.out.println(arr2==arr3);

    }
}