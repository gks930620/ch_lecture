public class P2switch문 {
    public static void main(String[] args) {
        //보통은 if else 쓰는데   너무 조건이 많을 때 ,조건이 간단할 때
        //쓸데가 많을 거같지만, 생각보다 잘 안씀. 파이썬은 switch가 없음.
        //주사위 번호 하나 뽑기,      입력받은 점수로 grade 나누기
        int num=2; //(int) (Math.random()*6) +1;
        switch (num){
            case 1 :
                System.out.println("1번");
                break;  //break가 없으면 다음것도 실행됨.
            case 2 :
                System.out.println("2번");   //2가나오면 지금 2,3번 실행됨
            case 3 :
                System.out.println("3번");
                break;
            default:
                System.out.println("좀 큰숫자들");
                break;
        }
    }
}
