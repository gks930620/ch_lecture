package p5.engineer;

public class Calculator {
    public double sin(double num){
        return Math.sin(num);
    }
    /**
     *
     * @return  a^b
     */
    public int pow(int a, int b){
        int result=1;
        for(int i=0 ; i<b ; i++){
            result*=a;   //a를 b번 곱한다.  b가 0이면 1
        }
        return result;
    }




}
