public class P5NanInfinity인데굳이하지말자 {
    public static void main(String[] args) {


        System.out.println(0/0.0);
        System.out.println(3/0.0);   //int 0으로 나누면 에러

        double nan=0/0.0;
        double infinity= 3/0.0;

        if(Double.isNaN(nan)){
            System.out.println("숫자가 아닌데요?.  0을 0으로 나누는 숫자는 없어용");
        }
        if(Double.isInfinite(infinity)) {
            System.out.println("무한대인데요? ");
        }
        //만날일 있나...


    }
}
