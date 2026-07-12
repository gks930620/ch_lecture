package P2람다만들기;

public class 람다기본메인3 {
    public static void main(String[] args) {
        //매개변수가 1개인거
//        Printable print1=new Printable() {
//            @Override
//            public void print(String str) {
//                System.out.println(str+"을 출력합니다.");
//                System.out.println(str+"이 출력되었습니다");
//            }
//        };

        //(매개변수) -> {실행문}
        Printable print1= (a)->{  //괄호를 유지한 버전
            System.out.println(a+"을 출력합니다.");
            System.out.println(a+"이 출력되었습니다");
        };
        print1.print("newjeans");

        //() 생략가능. 매개변수가 1개일때만
        Printable print2= a->{
            System.out.println(a+"을 출력합니다.");
            System.out.println(a+"이 출력되었습니다");
        };
        print2.print("newjeans");



    }
}
