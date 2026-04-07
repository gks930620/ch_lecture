package 예외떠넘기기;

public class 예외떠넘기기런타임예외Main {
    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4, 5, 3, 1, 2, 4, 5};  //
        printArr(arr); //문제없음
        int[] arr2 = {3, 1, 2, 4, 5};
        try {
            printArr(arr2); //index범위 초과 에러
            //printArr(null); //nullPointerException
        }catch (IndexOutOfBoundsException e) {
            System.out.println("길이가 10개 이상인 배열만 해야지");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("배열이 null이면 안되지");
            e.printStackTrace();
        } finally {
            System.out.println("수고하셨습니다.");
        }
    }


    //떠넘기는 쪽에 맡겼지만 try catch꼭 안해도 컴파일에러 안남.
    public static void printArr(int[] arr)  throws  IndexOutOfBoundsException,NullPointerException{
            for (int i = 0; i < 10; i++) {
                System.out.println(i + "번째 요소 : " + arr[i]);
            }
    }
}
