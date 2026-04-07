package 포장;

public class Wrapper메인 {
    public static void main(String[] args) {
        Integer integer=1000;  //기본타입의 값을 객체로 생성하기 위해
        int a=1000;
        System.out.println(a+integer);
        System.out.println(a==integer);
        Integer integer2=1000;
        System.out.println(integer==integer2);  //객체끼리는 주소연산

        int num=Integer.parseInt("333"); //유용한 static 메소드도 제공한다.
        System.out.println(num);

    }
}
