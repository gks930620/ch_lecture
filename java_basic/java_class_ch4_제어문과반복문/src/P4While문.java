public class P4While문 {
    public static void main(String[] args) {
        //while: 조건을 먼저 검사하고 반복. do-while: 본문을 먼저 1번 실행하고 조건 검사
        int sum=0;
        int i=1;  //카운트변수
        while (i<=10){
            sum+=i;
            i++;
        }
        System.out.println(sum );
        System.out.println("------------------------------------------------");

        //do-while: 최소 1회 실행이 보장됨. 사용자 입력을 무조건 한 번은 받아야 할 때 유용
        int num=100;
        do {
            System.out.println("do-while은 조건이 false여도 일단 1번은 실행됨. num=" + num);
            num++;
        } while (num<100);   //조건이 처음부터 false지만 위 본문은 이미 1번 실행됐음
        System.out.println("------------------------------------------------");

        //꼭 count가 필요한건 아님    주사위가 6이 나올때까지==break문에서 하자


    }
}
