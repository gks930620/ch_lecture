import java.util.Scanner;

public class P7예제 {
    public static void main(String[] args) {
        //입력받은 값이 1,2,3,4가 각각 예금 출금 잔고 종료임.
        //예금일 때 다음 입력으로 예금액을 입력받음
        // 출금일 때 다음 입력으로 출금액을 입력받음
        //  잔고일 때 현재 잔고가 출력됨
        // 종료는  프로그램 끝
        Scanner sc=new Scanner(System.in);
        int currentAmount=0;
        boolean flag=true;
        while (flag){
            System.out.println("1.예금  2.출금.  3.잔고출력.  4 종료");
            int select= sc.nextInt();
            int amount;
            switch (select){
                case 1:
                    System.out.print("예금액을 입력하세요");
                    amount=sc.nextInt();
                    currentAmount+=amount;
                    break;
                case  2:
                    System.out.print("출금액을 입력하세요");
                    amount=sc.nextInt();
                    if(amount>currentAmount){   //잔고보다 많이 출금 못하게 검증
                        System.out.println("잔고가 부족합니다. 현재 잔고: " + currentAmount);
                    }else {
                        currentAmount-=amount;
                    }
                    break;
                case  3:
                    System.out.println("현재 잔고는 " + currentAmount + " 입니다");
                    break;
                case 4:
                    flag=false;
                    break;
                default:
                    System.out.println("1~4 중에서 선택하세요");
            }

        }



    }
}
