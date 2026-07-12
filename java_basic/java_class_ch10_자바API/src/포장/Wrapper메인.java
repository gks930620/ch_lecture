package 포장;

public class Wrapper메인 {
    public static void main(String[] args) {
        Integer integer=1000;  //기본타입의 값을 객체로 생성하기 위해
        int a=1000;
        System.out.println(a+integer);
        System.out.println(a==integer);  //true. 기본타입과 비교하면 Integer가 언박싱되어 "값"끼리 비교
        Integer integer2=1000;
        System.out.println(integer==integer2);  //false. 객체끼리는 주소연산
        //참고 : -128~127 범위의 값은 Integer가 미리 만들어 둔 객체(캐시)를 재사용해서
        //Integer b=100; Integer c=100; 이면 b==c가 true가 됨. 그래서 값 비교는 항상 equals로!

        int num=Integer.parseInt("333"); //유용한 static 메소드도 제공한다.
        System.out.println(num);

    }
}
