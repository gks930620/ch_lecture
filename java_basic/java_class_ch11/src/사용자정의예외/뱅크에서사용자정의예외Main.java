package 사용자정의예외;

public class 뱅크에서사용자정의예외Main {
    public static void main(String[] args) {
        Account account=new Account();
        account.money=10000;
        try{
            account.withdraw(20000);  //일반예외니까 컴파일에러 뜸
        }catch (InsufficientException e){
            System.out.println("돈이 부족합니다.");
        }
    }
}
