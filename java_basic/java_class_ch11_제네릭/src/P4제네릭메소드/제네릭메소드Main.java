package P4제네릭메소드;

public class 제네릭메소드Main {
    public static void main(String[] args) {
        //제네릭 메소드는  제네릭타입을 리턴하는 메소드
        //  T에 해당하는 걸 그냥 return하는 메소드랑  Box<T>를 return하는 메소드는 다르다.
        // Box<String> 이랑 Box<Integer>는 엄연히 다른 타입.

        Box<String> boxString = boxing("boxString");
        System.out.println(boxString.getContent());

        Box<Integer> boxInteger = boxing(500);
        System.out.println(boxInteger.getContent());
    }
    private  static <T> Box<T> boxing(T t){
        Box<T> box = new Box<>(t);
        return box;
    }

}
