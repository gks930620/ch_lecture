package 사용자정의예외;

//일반예외 : 인출할 때 돈이 모자라면 발생 시키려고 만든 예외
//자바 문법상 예외는 아니지만. 개발자가 생각하기에 예외인 경우
public class InsufficientException  extends  Exception{

    public InsufficientException() {
        super();
    }

    public InsufficientException(String message) {
        super(message);
    }

    public InsufficientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientException(Throwable cause) {
        super(cause);
    }
}
