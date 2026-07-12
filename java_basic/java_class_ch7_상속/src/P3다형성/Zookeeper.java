package P3다형성;

public class Zookeeper {
    // 매개변수를 부모 타입(Animal)으로 받으면 모든 하위 동물을 하나의 메소드로 처리할 수 있다.
    // 새로운 동물이 추가돼도 이 메소드는 고칠 필요가 없다(OCP: 확장에는 열리고 수정에는 닫힘).
    public void feed(Animal animal) {
        System.out.print("사육사가 먹이를 줍니다 → ");
        animal.sound();  // 실제 객체에 따라 다른 소리가 난다(다형성)
    }
}
