package P3다형성;

public class 매개변수다형성Main {
    public static void main(String[] args) {
        // 하나의 feed(Animal) 메소드로 어떤 동물이든 처리할 수 있다 — 매개변수의 다형성
        Zookeeper keeper = new Zookeeper();

        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal bird = new Bird();

        keeper.feed(dog);   // 사육사가 먹이를 줍니다 → 멍멍!
        keeper.feed(cat);   // 사육사가 먹이를 줍니다 → 야옹~
        keeper.feed(bird);  // 사육사가 먹이를 줍니다 → 짹짹!
    }
}
