package 사용자정의예외;

public class Account {
    public  int money;

    public void deposit(int money){
        this.money+=money;
    }
    public void withdraw(int money) throws InsufficientException{
        if(this.money<money) throw new InsufficientException(); //의도적으로 이 에러를 발생시킴
        this.money-=money;
    }

}
