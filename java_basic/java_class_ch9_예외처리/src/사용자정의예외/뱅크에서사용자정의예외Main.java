package 사용자정의예외;

public class 뱅크에서사용자정의예외Main {
    public static void main(String[] args) {
        Account account=new Account();
        account.money=10000;
        try{
            account.withdraw(20000);  //실행예외라서 try-catch가 강제되진 않지만, 잡아서 처리할 수 있다
        }catch (InsufficientException e){
            System.out.println("돈이 부족합니다.");
        }
    }
}
