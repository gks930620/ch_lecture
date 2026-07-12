package p5;

public class P5패키지와import {
    // 클래스 이름이 같아도 패키지가 다르면 다른 패키지
    public static void main(String[] args) {
        //같은 Calculator여도 어떤  Calculator이냐에 따라 안의 있는 내용이 다름
        //대표적인 예로 Date가 있음.  sql Date, util Date

        //이름이 같은 클래스를 함께 쓸 때는 패키지 이름까지 붙여서(전체 이름으로) 구분한다.
        p5.basic.Calculator basicCal = new p5.basic.Calculator();
        System.out.println("basic sum(2,3) : " + basicCal.sum(2, 3));

        p5.engineer.Calculator engineerCal = new p5.engineer.Calculator();
        System.out.println("engineer pow(2,3) : " + engineerCal.pow(2, 3));
        System.out.println("engineer pow(5,0) : " + engineerCal.pow(5, 0));
    }

}
