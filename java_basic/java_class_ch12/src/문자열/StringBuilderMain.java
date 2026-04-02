package 문자열;

public class StringBuilderMain {
    public static void main(String[] args) {
        //StringBuffer : 스레드 안전.  나머지 스레드들이 데이터접근불가
        //StringBuilder : 스레드 비안전. 나머지 스레드들이 접근가능. buffer보다 빠름
        //근데 사실 둘다 String보다 무척 빠름.

        String str="a";
        str=str+"b";   //a,b, ab 객체가 생김. ->느림

        StringBuilder sb=new StringBuilder(); //문자열 연산을 많이한다면 StringBuilder로
        sb.append("a").append("b").insert(0,"ABC") .delete(3,4); //메소드체이닝
//        sb.append("a");
//        sb.append("b"); ....  하나씩하는거랑 똑같다.
        System.out.println(sb.toString());


    }
}
