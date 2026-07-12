package 문자열;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StringMain {
    public static void main(String[] args) {
        //기본 메소드. equals,charAt, substring,length,replace,indexOf,split 등
        //외부파일을 읽어서 문자열로 저장하는 경우
        // 반대로 문자열을 파일로 변경할 때  byte[]을 이용. 이진데이터
        String str="한";
        byte[] arr1= str.getBytes(StandardCharsets.UTF_8); //인코딩은 상수로 명시하는 게 안전
        System.out.println(Arrays.toString(arr1));
        byte[] arr2={100,101,102,103,104,105};
        String str2=new String(arr2, StandardCharsets.UTF_8);
        System.out.println(str2);
    }
}
