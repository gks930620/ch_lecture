public class P6비교논리연산자2 {
    public static void main(String[] args) {
        // & | 는 사용X.   && || 사용
        //책에 나오는 xor은 안씀.
        char ch='B';
        if (65<=ch && ch<=90 ){
            System.out.println("대문자");
        }
        int num=6;
        if(num%2==0 || num%3==0){
            System.out.println("2또는 3의 배수군");
        }

    }
}
