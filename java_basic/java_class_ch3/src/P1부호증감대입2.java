public class P1부호증감대입2 {
    public static void main(String[] args) {
        int a=10;
        a+=2; //a= a+2;           12
        a/=3;  // a= a/3;         4
        a*=2;   // a=a*2;         8
        a-=2;   // a= a-2         6
        System.out.println(a);

        int b=0;
        b=-2;   // 연산자가 아니라 그냥 b에 -2할당.    -라면 쉽게 찾았을 텐데..
        b=+2;   //  연산자가 아니라 그냥 b에 2할당.

        //이것만 설명하자. 복잡한 상황에 대해서는 굳이 설명 안해도 됨
    }
}
