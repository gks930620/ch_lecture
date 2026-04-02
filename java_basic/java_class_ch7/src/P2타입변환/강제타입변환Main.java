package P2타입변환;

public class 강제타입변환Main {
    public static void main(String[] args) {
        Animal animalButRealCat=new Cat();
        animalButRealCat.eat();   //오버라이딩된 메소드 실행
        Cat cat=(Cat)animalButRealCat;  //타입이 Animal이던거를 하위타입인 Cat으로
        
        Animal justAnimal=new Animal(); //실제로도 동물
        justAnimal.eat(); //동물의 eat
        Cat cat2= (Cat)justAnimal; //에러남
        //ClassCastException :  타입변환할 때 불가능한 경우 발생하는 exception

    }
}
