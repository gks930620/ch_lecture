public class P8재귀메소드맛보기 {
    public static void main(String[] args) {
        //재귀 메소드 : 자기 자신을 호출하는 메소드.  맛보기로 factorial만.
        // factorial :  5! = 5 X 4 X 3 X 2 X 1
        System.out.println(factorial(5));   //120

        //종료 조건(base case)이 없으면 계속 자기 자신을 호출하다가 StackOverflowError 발생
    }

    public static int factorial(int n){
        if(n <= 1) return 1;   //종료 조건. 이게 없으면 무한 호출
        return n * factorial(n - 1);
    }
}
