import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class P5배열 {
    public static void main(String[] args) throws IOException {
        //Array
        //같은 타입의 값만 관리한다.  배열의 길이는 정해져있다.
        // 걸그룹 전부 변수로 선언하기엔 너무 많다. 비슷한 데이터를 한 곳에 모아 관리할 수 있게...
        //선언,초기화 방법을 전부 외워야 합니다.
        //배열의 초기값
        int[] intArr={3,1,2,4,5};
        int[] intArr2=new int[5];

        //어떤 방법이든 내가 가진 데이터를 배열에 저장할 수 있으면 그걸로 OK
        //중요 : int[] arr. 원소는int지만 배열이기때문에 heap에 생성.  arr[0]은 heap에 있는 원소의 값에 int값이 할당
        // String[] arr .   배열이기때문에 heap에 생성.  arr[0] heap에 있는 원소의 값에 String 객체(arr말고 heap영역 다른곳에 생긴) 주소값이 할당

        //배열의 합,평균, 최대 최소 값 구하는 문제
        int sum=0;
        for( int i=0  ;i<intArr.length ; i++){
            sum+=intArr[i];
        }



    }
}
