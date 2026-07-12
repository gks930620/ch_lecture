package ch2_variable;

public class P5문자열 {
    public static void main(String[] args) {
        String str1="";                 //빈 문자열도 가능
        String hello="Hello World";
        System.out.println(str1);       //빈 문자열은 아무것도 출력되지 않음
        System.out.println(hello);

        String newjeans="newjeans";
        System.out.print(newjeans + "\n"); //  \\ , \n , \t
        System.out.println("사랑해요");
        // \가 왜있냐.  출력문에 "를 출력하고 싶어요.
        System.out.println("newjeans :   \"sweet like bubble gum\" ");

    }
}
