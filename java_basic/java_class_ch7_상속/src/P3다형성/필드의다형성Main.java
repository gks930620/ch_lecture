package P3다형성;

public class 필드의다형성Main {
    public static void main(String[] args) {
        // 다형성 : 사용 방법(owner.pet.sound())은 똑같지만
        //          pet에 담긴 실제 객체에 따라 결과가 달라진다.
        Owner owner1 = new Owner();
        owner1.pet = new Dog();   // pet 필드 타입은 Animal, 실제 객체는 Dog

        Owner owner2 = new Owner();
        owner2.pet = new Cat();

        owner1.pet.sound();  // 멍멍!
        owner2.pet.sound();  // 야옹~
    }
}
