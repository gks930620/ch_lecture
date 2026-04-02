package P4추상클래스;

public abstract class Pet {  //abstract : 추상적인

    public  abstract void walk();
    //추상메소드 : 반드시 하위 클래스에서 재정의 하세요. 추상메소드들은 재정의 안하면 에러남.
    public  abstract void eat();

    public void call(){
        System.out.println("주인을 부릅니다.");
    }
    //재정의해도됩니다. 안해도 됩니다.

}
