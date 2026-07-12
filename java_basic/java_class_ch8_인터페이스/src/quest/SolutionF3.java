package quest;

// F-3. 전략 패턴 + 런타임 교체
public class SolutionF3 {

    // 전략 계약: 할인 방법
    interface DiscountStrategy {
        long discount(long price);
    }

    static class NoDiscount implements DiscountStrategy {
        @Override
        public long discount(long price) {
            return price;
        }
    }

    static class PercentDiscount implements DiscountStrategy {
        private final int percent;

        PercentDiscount(int percent) {
            this.percent = percent;
        }

        @Override
        public long discount(long price) {
            return price - price * percent / 100;
        }
    }

    static class FixedDiscount implements DiscountStrategy {
        private final long amount;

        FixedDiscount(long amount) {
            this.amount = amount;
        }

        @Override
        public long discount(long price) {
            return Math.max(0, price - amount);
        }
    }

    // 컨텍스트: 전략을 필드로 갖고, 런타임에 교체 가능
    static class PriceCalculator {
        private DiscountStrategy strategy;

        PriceCalculator(DiscountStrategy strategy) {
            this.strategy = strategy;
        }

        void changeStrategy(DiscountStrategy strategy) { // 런타임 교체 기능
            this.strategy = strategy;
        }

        long finalPrice(long price) {
            return strategy.discount(price);
        }
    }

    public static void main(String[] args) {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        System.out.println(calculator.finalPrice(10_000)); // 10000

        calculator.changeStrategy(new PercentDiscount(10)); // 10% 할인으로 교체
        System.out.println(calculator.finalPrice(10_000)); // 9000

        calculator.changeStrategy(new FixedDiscount(3_000)); // 3000원 정액 할인으로 교체
        System.out.println(calculator.finalPrice(10_000)); // 7000

        // 함수형 인터페이스이므로 람다로 즉석 전략도 가능
        calculator.changeStrategy(price -> price / 2); // 반값 이벤트
        System.out.println(calculator.finalPrice(10_000)); // 5000
    }
}
