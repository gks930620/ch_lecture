public class P4정확한계산 {
    public static void main(String[] args) {
        double pineUnit=0.1;
        double number=7.0;
        System.out.println(pineUnit*number);

        int unit=1;
        int numberInt=7;
        System.out.println(unit*numberInt/10.0);
        //소수점 계산이 뭔가 안 맞아 떨어진다... => 소수 표현방식때문에
        //이 사실만 알면 문제 만났을 때 쉽게 해결할 수 있음.
    }
}
