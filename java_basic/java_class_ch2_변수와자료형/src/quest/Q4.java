package quest;

import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("500원 짜리의 동전의 갯수 : " );
        int coin500=scanner.nextInt();
        System.out.println("100원 짜리의 동전의 갯수 : " );
        int coin100=scanner.nextInt();
        System.out.println("50원 짜리의 동전의 갯수 : " );
        int coin50=scanner.nextInt();
        System.out.println("10원 짜리의 동전의 갯수 : " );
        int coin10=scanner.nextInt();
        int amount= coin500*500 + coin100*100 + coin50*50 + coin10*10;
        System.out.println("저금통안의 동전의 총 액수 " + amount);
    }
}
