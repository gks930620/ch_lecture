public class P6삼항연산자 {
    public static void main(String[] args) {
        //왜 삼항연산자 빠짐?
        //삼항연산자의 결과=하나의 값

        int a= 3<5 ? 3 : 5;
        int b= true ? 100 : 200;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println( 5<3 ? "5가 3보다 작냐?" : "5는 3보다 크잖아");

    }

}
