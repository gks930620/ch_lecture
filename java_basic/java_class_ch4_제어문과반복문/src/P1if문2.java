public class P1if문2 {
    public static void main(String[] args) {
        //중첩 if문도 가능. 한번에 할지. 중첩해서 들어갈지는 경험으로 판단.
        int score=95;
        if(score >=90 ){
            System.out.println("일단 A는 확정이군");
            if(score >=95){
                    System.out.println("자네는 A+일세 ");
            }else{
                    System.out.println("자네는 A일세 ");
            }
        }
    }
}
