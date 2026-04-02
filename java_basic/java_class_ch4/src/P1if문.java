import java.util.Random;

public class P1if문 {
    public static void main(String[] args) {
        int score=93;
        if(score>=90){
            System.out.println("점수가 90보다 큽니다");
        }else if(score<90 && score>=80){
            System.out.println("점수가 80점대입니다.");
        }  else{
            System.out.println("80점 미만이라니...");
        }
    }
}
