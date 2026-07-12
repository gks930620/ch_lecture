package ch2_variable;

import java.util.Arrays;

//강의 문서 ch2의 핵심 예제 모음
public class P9핵심예제모음 {
    public static void main(String[] args) {
        //1. int 오버플로우 : 최댓값에서 1을 더하면 최솟값으로 돌아간다
        int max = Integer.MAX_VALUE;
        System.out.println(max);        // 2147483647
        System.out.println(max + 1);    // -2147483648 (오버플로우!)

        //2. 부동소수점 오차 : 0.1 + 0.2는 정확히 0.3이 아니다
        System.out.println(0.1 + 0.2);  // 0.30000000000000004

        //3. String 리터럴 vs new String, == vs equals
        String s1 = "java";             //리터럴은 문자열 풀에서 공유
        String s2 = "java";
        String s3 = new String("java"); //new는 항상 새 객체 생성
        System.out.println(s1 == s2);       // true  (같은 풀 객체)
        System.out.println(s1 == s3);       // false (참조가 다름)
        System.out.println(s1.equals(s3));  // true  (내용 비교는 equals!)

        //4. 배열 참조 공유 vs 실제 복사
        int[] a = {1, 2, 3};
        int[] b = a;                    //참조만 복사 (같은 배열을 가리킴)
        int[] c = Arrays.copyOf(a, a.length);  //실제 복사 (새 배열)
        a[0] = 99;
        System.out.println(b[0]);       // 99 (a와 같이 바뀜)
        System.out.println(c[0]);       // 1  (영향 없음)

        //5. final 상수 : 한 번 대입하면 바꿀 수 없다
        final int MAX_SPEED = 100;
        //MAX_SPEED = 200;  //컴파일 에러! final 변수는 재대입 불가

        //6. var 타입 추론 : 대입하는 값을 보고 컴파일러가 타입을 정한다
        var count = 10;         //int로 추론
        var name = "홍길동";     //String으로 추론
        System.out.println(count + ", " + name);
    }
}
