package P2람다만들기;

public class 람다기본메인5 {
    public static void main(String[] args) {
        //실행문이 1줄인데.... return만  하는거.
        // (매개변수) -> {실행문}
        Sumable sumable= (a, b) -> {
          return   a+b ;
        };
        //Sumable sumable= (a, b) -> a+b;

        //실행문에서 {} 빼고 return도 빼고 return할 값만 써도 됨.

        System.out.println(sumable.sum(3,5)) ;

    }
}
