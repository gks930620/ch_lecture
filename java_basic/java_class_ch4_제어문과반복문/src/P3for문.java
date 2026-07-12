public class P3for문 {
    public static void main(String[] args) {
        int sum=0;
        sum=sum+1;
        sum=sum+2;
        sum=sum+3;
        sum=sum+4;
        sum=sum+5;
        System.out.println(sum);

        sum=0;
        for(int i=1 ; i<=5 ; i++){   //i는 for문에서만 사용하는 변수
            sum+=i;   //sum=sum+i;
        }
        System.out.println(sum);
        //참고: 증감식 자리에 콤마로 여러 식을 쓸 수도 있음 ex) for(int i=1; i<=5; sum+=i,i++){}
        //      근데 읽기 어려우니까 기본형태로 쓰자

        System.out.println("----------------------------");
        int jjakSum=0;
        int holSum=0;
        for(int i=0, j=1 ; i<10 && j<10 ; i+=2,j+=2){
            jjakSum+=i;
            holSum+=j;
        }
        System.out.println("홀수 " + holSum);
        System.out.println("짝수 " + jjakSum);



    }
}
