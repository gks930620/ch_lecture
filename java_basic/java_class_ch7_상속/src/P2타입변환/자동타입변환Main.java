package P2타입변환;

public class 자동타입변환Main {
    public static void main(String[] args) {
        //타입변환+다형성  매우 중요
        //강제타입변환 어렵긴 하지만 나중에(제네릭에서) 이해하려면
        // 쓰이니까 짚고 넘어가자.

        //상위타입 변수= 하위타입 객체 ==  자동 타입 변환 ;
        Animal cat= new Cat();  //타입은 Animal, 실제 객체는 Cat
        Animal animal=new Animal();
        cat.eat();
        animal.eat();




    }
}
