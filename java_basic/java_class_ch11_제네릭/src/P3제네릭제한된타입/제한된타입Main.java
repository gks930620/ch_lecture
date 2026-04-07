package P3제네릭제한된타입;

public class 제한된타입Main {

    public static void main(String[] args) {
        //가볍게 듣자 가볍게.
        // 제네릭의 타입을 제한함.
        Box<Cat> catBox=new Box<>(new Cat());
        Box<Dog> dogBox=new Box<>(new Dog());
        //Box<String> strBox = new Box<>("aa");  String은 Animal의 하위클래스아님. 에러.

    }

}
