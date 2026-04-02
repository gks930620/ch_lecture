package P3다형성;

import P3다형성.Idol.Fan;
import P3다형성.Idol.Idol;
import P3다형성.Idol.Ive;
import P3다형성.Idol.Newjeans;

public class 필드의다형성Main {
    public static void main(String[] args) {
        //다형성 : 사용방법은 동일하지만 실행결과가 다양하게 나오는 것
        //필드, 매개변수 등
        Idol newjeans=new Newjeans();
        Idol ive=new Ive();

        Fan fan1= new Fan();
        fan1.myFavorite=newjeans;

        Fan fan2= new Fan();
        fan2.myFavorite=ive;

        fan1.myFavorite.sing();
        fan2.myFavorite.sing();



    }
}








