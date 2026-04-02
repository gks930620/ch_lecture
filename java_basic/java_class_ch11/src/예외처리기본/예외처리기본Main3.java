package 예외처리기본;

public class 예외처리기본Main3 {
    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4, 5, 3, 1, 2, 4, 5};  //
        printArr(arr); //문제없음
        int[] arr2 = {3, 1, 2, 4, 5};
        printArr(arr2); //index범위 초과 에러
        //printArr(null); //nullPointerException
    }

    //RuntimeException은 try-cath 안 해도 컴파일에러X

    public static void printArr(int[] arr) {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(i + "번째 요소 : " + arr[i]);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("길이가 10개 이상인 배열만 해야지");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("배열이 null이면 안되지");
            e.printStackTrace();
        } finally {
            System.out.println("수고하셨습니다.");
        }
        //catch(IndexOutOfBoundsException  | NullPointerException e)  Exception도 클래스
    }
}
