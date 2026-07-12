package p6;

public class P6접근제한자 {
    public static void main(String[] args) {
        //public과 private만 알면 됩니다.
        Person person1=new Person("한창희",30,0);
        //private가 된 필드와 메소드는 외부에서 사용 불가능.   내부에서는 사용가능
        //아래 주석을 해제하면 컴파일 오류가 발생한다. (private 접근 불가 확인용)
//        person1.money=10000;
//        person1.name="한창희";
//        person1.age=30;
        Person person2=new Person("박지성",42,50000);
//        person1.earn(10000);   //private 메소드도 외부에서 호출 불가 (컴파일 오류)
        person2.giveMoney(person1, 30000);
        person1.printMoney();
        person2.printMoney();
    }
}
