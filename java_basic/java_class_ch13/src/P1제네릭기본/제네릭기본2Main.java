package P1제네릭기본;

public class 제네릭기본2Main {
    public static void main(String[] args) {
        //"객체를 만들 때" 필드(or returnType,매개변수)의 타입을 지정해주는거임.
        // Box객체마다 필드 content는 정해진 하나의 타입이 되는 것

        //제네릭이란 결정되지 않은 타입을 파라미터로 처리하고,
        //실제 사용할 때 파라미터를 구체적인 타입을 대체시키는 기능
        Box<String> box=new Box<>("ABC"); //box의 필드(content)는 무조건 String
        Box<Integer> box2= new Box<>(30); //box2의 필드는 무조건 Integer. 참고로 객체만 된다...

        String boxStr=box.getContent();
        int box2Int=box2.getContent();   //타입변환 없이 사용가능
        System.out.println(boxStr);
        System.out.println(box2Int);
        //Box<Integer> box3= new Box<>("");
        //box3의 필드(content)는 무조건 Integer로 만들으라고 명시했는데 String값이 오려고하니까 컴파일단계에서 에러


    }
}
