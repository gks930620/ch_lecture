package System;

public class System메인 {
    public static void main(String[] args) {
        //입력,출력, 시스템 시간, 종료 등
        //입력 출력은 많이 해봤고 시간만 조금 해보자.
        long currentTime = System.currentTimeMillis();
        long sum=0;
        for(int i=0 ; i<100000000 ; i++){
            sum=sum+i;
        }
        System.out.println(sum);
        long lastTime = System.currentTimeMillis();
        System.out.println("걸린시간 : " + (lastTime-currentTime) +"ms");

    }
}
