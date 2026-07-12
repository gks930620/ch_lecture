public class P1메모리영역2 {
    public static void main(String[] args) {
        // P1메모리영역과 결과는 같지만, 배열을 만드는 표기법을 바꿔 본 버전이다.
        //   - P1메모리영역:   int[] arr = {1,2,3};        (축약형)
        //   - 여기(P1메모리영역2): int[] arr = new int[]{1,2,3};  (정식형)
        // 어느 쪽이든 new 로 새 배열 객체가 heap에 각각 생기므로 arr1==arr2 는 false다.
        int[] arr1=new int[]{1,2,3};
        int[] arr2=new int[]{1,2,3};
        int[] arr3=arr2;            // 주소를 복사 -> 같은 객체를 가리킴
        System.out.println(arr1==arr2);  // false (서로 다른 객체)
        System.out.println(arr2==arr3);  // true  (같은 객체를 가리킴)

    }
}