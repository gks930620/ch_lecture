package ch2_variable;

public class P4문자 {
    public static void main(String[] args) {
        //ASCII 코드 설명.  자바에서는 유니코드로 .
        char var1='A';
        char var2='가';
        char a=65;
        System.out.println(var1==a);
        System.out.println('A'==65);   //char는 숫자로 변경후 연산
        char b=(char)(a+1);
        System.out.println(b);
        
        //알파벳 전부 출력 (반복문은 ch4에서 배움 — 지금은 참고만)
        for (char c = 'A'; c <= 'Z'; c = (char)(c + 1)) {
            System.out.print(c);
        }
        System.out.println();
    }
}
