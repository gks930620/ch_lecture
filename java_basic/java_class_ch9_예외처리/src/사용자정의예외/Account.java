package 사용자정의예외;

public class Account {
    public  int money;

    public void deposit(int money){
        this.money+=money;
    }
    //실행예외(RuntimeException)라서 throws 선언이 강제되지 않는다.
    public void withdraw(int money){
        if(this.money<money) throw new InsufficientException(); //의도적으로 이 에러를 발생시킴
        this.money-=money;
    }

}
