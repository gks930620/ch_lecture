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
        for(int i=0 ; i<b ; i++){
            a*=a;
        }
        return a;
    }




}
