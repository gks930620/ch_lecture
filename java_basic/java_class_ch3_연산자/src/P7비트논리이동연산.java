public class P7비트논리이동연산 {
    public static void main(String[] args) {
        //특별한 경우 아니면 안 써도 됨.
        // CPU에 직접 계산해서 빠른 장점이 있지만
        // 순서도 지극히 상식적으로 생각하면 됨.

        //비트 논리 연산
        int a = 0b1100;   //12
        int b = 0b1010;   //10
        System.out.println(a & b);   //8  (0b1000) 둘 다 1인 자리만 1
        System.out.println(a | b);   //14 (0b1110) 하나라도 1이면 1
        System.out.println(a ^ b);   //6  (0b0110) 서로 다르면 1
        System.out.println(~a);      //-13 비트 반전

        System.out.println("------------------------------");
        //시프트(이동) 연산
        int x = 3;                    //0011
        System.out.println(x << 2);   //12 (1100) 왼쪽으로 밀고 빈칸은 0

        int y = -8;
        System.out.println(y >> 1);   //-4 부호비트 유지하며 오른쪽 이동
        System.out.println(y >>> 1);  //2147483644 왼쪽을 0으로 채움 => 큰 양수
    }
}
