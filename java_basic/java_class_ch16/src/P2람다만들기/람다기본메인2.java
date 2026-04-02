package P2람다만들기;

public class 람다기본메인2 {
    public static void main(String[] args) {
        //인터페이스를 람다(함수)로 만들어봅시다.
//        Singable newjeans=new Singable() {
//            @Override
//            public void sing() {
//                System.out.println("뉴진스가 노래합니다.!!!");
//            }
//        };
        /// (매개변수) -> {실행문}
        Singable newjeans=()->{
            //내가 원하는 내용 작성!!!
            System.out.println("뉴진스가 노래합니다");
            System.out.println("supernatural");
        };
        newjeans.sing();

        Singable ive=()->{
            System.out.println("아이브 노래합니다");
            System.out.println("eleven");
            System.out.println("love dive");
            System.out.println("해야");
        };
        ive.sing();


    }
}
