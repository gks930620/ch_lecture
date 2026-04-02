package P3제네릭제한된타입;

public class Box<T extends  Animal>{
    private  T animal;

    public Box(T animal) {
        this.animal = animal;
    }

    public T getAnimal() {
        return animal;
    }
}
