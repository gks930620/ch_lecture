import java.time.LocalDateTime;

public class P1메모리영역 {
    public static void main(String[] args) {
        //참조타입은 heap영역에 생기고
        // stack영역은 값이 생김.
        // stack영역의 변수 타입이 참조타입이면
        // stack영역의 변수 값=  heap어딘가에 생긴 주소 값
        //참조타입= literal 제외하고 전부
        // 우리가 만든 객체 or 자바에서 기본적으로 제공해주는 객체
        int[] arr1={1,2,3};
        int[] arr2={1,2,3};
        int[] arr3=arr2;
        System.out.println(arr1==arr2);
        System.out.println(arr2==arr3);

    }
}