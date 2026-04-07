
public class P1부호증감대입 {
    public static void main(String[] args) {
        //클래스파일 여러개 만들어도 좋으니까 결과 쉽게 확인할 수 있게
        int x=100;
        System.out.println("-x : " + -x);

        x=10;
        int y=10;
        int z;
        z= x++ + ++y;
        System.out.println(z);
        System.out.println("-================");
        x=10; y=10;
        z=++x-y;
        System.out.println(x);
        System.out.println(z);
        System.out.println("-================");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        //전위 후위연산자차이
        // 후위연산자는 변수값을 임시저장하는 임시변수 생성 => 메모리 낭비
        // 인데.... 자바에서는 별로 안 중요함. 그래서 그런지 전위연산자 쓰는사람 못봄
        // 왠만하면 후위연산자 사용하세요.

    }
}



