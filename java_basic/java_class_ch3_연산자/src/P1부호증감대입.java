
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
        // 전위(++x): 값을 먼저 1 증가시킨 뒤 그 값을 식에서 사용
        // 후위(x++): 현재 값을 식에서 먼저 사용한 뒤 1 증가
        // 성능 차이는 사실상 없음(컴파일러가 알아서 최적화함)
        // 자바에서는 관례적으로 후위연산자를 많이 씀. 웬만하면 후위연산자 사용하세요.

    }
}



