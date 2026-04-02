package P2람다만들기;

public class 람다기본메인 {
    public static void main(String[] args) {
        //변수의 값으로 함수를 할당가능.   함수가 하나의 값이 된거죠.
        //자바에서는 함수역할을 인터페이스가 합니다.
        // 그 중에서 인터페이스의 "추상메소드가 1개"인 인터페이스만 가능.
        // 이 인터페이스의 익명구현객체를 람다식으로 표현  ->

        //익명구현객체를 람다식으로 바꾸는 공식
        // (매개변수) -> {실행문}
        // 책에 나오는 많은 예제는 이 공식의 변형일 뿐
        
       /*
       함수타입 변수 =함수;
        method1(변수);

        public static void method1(함수타입 함수){
            이전작업..
            함수.do();
            후작업...
        }
         */


        Workable workable= (a, b) -> {};
        Workable workable2= (name, job) -> {};

    }




}
