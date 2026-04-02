package 문자열;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class StringMain {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //기본 메소드. equals,charAt, substring,length,replace,indexOf,split 등
        //외부파일을 읽어서 문자열로 저장하는 경우 
        // 반대로 문자열을 파일로 변경할 때  byte[]을 이용. 이진데이터
        //StringTokenizer는 굳이???
        String str="한";
        byte[] arr1= str.getBytes("UTF-8");
        System.out.println(Arrays.toString(arr1));
        byte[] arr2={100,101,102,103,104,105};
        String str2=new String(arr2);
        System.out.println(str2);
    }
}
