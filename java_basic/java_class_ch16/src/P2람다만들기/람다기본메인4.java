package P2람다만들기;

public class 람다기본메인4 {
    public static void main(String[] args) {
        //실행문이 1줄인거.  근데 return은 없는..
        // (매개변수) -> {실행문}
        Printable print1=  a-> System.out.println(a+"을 출력합니다.")  ;
        // {실행문}이 1줄일 때   {}생략가능
        print1.print("newjeans");



    }
}
