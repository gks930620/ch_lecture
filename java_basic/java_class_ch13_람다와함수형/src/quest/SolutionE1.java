package quest;

import java.util.function.UnaryOperator;

// E-1: 할인 정책을 함수형으로 설계하고 런타임에 교체
public class SolutionE1 {
    static class PriceCalculator {
        private UnaryOperator<Integer> discountPolicy;

        PriceCalculator(UnaryOperator<Integer> discountPolicy) {
            this.discountPolicy = discountPolicy;
        }
        void changePolicy(UnaryOperator<Integer> policy) { // 런타임 교체
            this.discountPolicy = policy;
        }
        int finalPrice(int price) {
            return discountPolicy.apply(price);
        }
    }

    public static void main(String[] args) {
        UnaryOperator<Integer> tenPercent = price -> price * 90 / 100;
        UnaryOperator<Integer> flat1000 = price -> Math.max(0, price - 1000);

        PriceCalculator calc = new PriceCalculator(tenPercent);
        System.out.println(calc.finalPrice(20000)); // 18000

        calc.changePolicy(flat1000); // 정책 교체
        System.out.println(calc.finalPrice(20000)); // 19000
    }
}
