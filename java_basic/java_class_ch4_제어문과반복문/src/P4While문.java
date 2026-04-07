public class P4While문 {
    public static void main(String[] args) {
        //while, do-while있지만,   do-while 책에서 말고 다른데서 본적이 있나 싶음
        //한번 무조건 실행하겠다 하는건데. 애초에 while문 조건식을 잘 쓰면 됨
        int sum=0;
        int i=1;  //카운트변수
        while (i<=10){
            sum+=i;
            i++;
        }
        System.out.println(sum );
        System.out.println("------------------------------------------------");

        //꼭 count가 필요한건 아님    주사위가 6이 나올때까지==break문에서 하자


    }
}
