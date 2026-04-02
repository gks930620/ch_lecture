package 리플렉션과어노테이션;

public class P1리플렉션객체얻기Main {
    public static void main(String[] args) throws ClassNotFoundException {
        //제네릭 하고나서 하자.  블로그 제네릭 글 살짝 고치자. raw타입 쓴거 안됨
//        class: 우리가 객체와클래스 배울 때 배우던 그거.
//        Class: 자바의 여러 class 중 하나
//       헷갈리니까 Class는 그대로 Class로,    class는  한글로 클래스 라고 하겠다

//        특정 클래스의 정보들인 Field, Method, 클래스 이름 등에 대한 정보들을
//        관리하는 것이 Reflection
        Class<Square> clazz = Square.class;
        Class<?> clazz2 = Class.forName("리플렉션과어노테이션.Square");
        //둘다 Class< >를 제공하지만  Class.forName은 어떤 타입이 될지 모름

        //Class<Square>에는  Square클래스에 대한 정보가 있음



    }
}
