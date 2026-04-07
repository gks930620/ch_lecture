package P1제네릭기본;

public class 제네릭기본Main {
    public static void main(String[] args) {
        //제네릭이 왜 생겨났나를 생각해보세요.
        Box box=new Box("string");  //content가 String 타입
        Box box2= new Box(30);   //  content가 Integer타입
        Box box3= new Box("문자열인데");

        String str=(String) box.getContent();   //필드를 원래 타입에 맞춰서 사용하려면 형변환해야됨
        Integer integer=(Integer) box2.getContent();
        System.out.println(str.substring(0,2));
        System.out.println(integer);

        int a=(Integer)box3.getContent();  //컴파일에러안뜸
        System.out.println(a);  //런타임에 에러를 알게됨.

        //Box를 제네릭에 적용해도 여전히 동작은 함.
        //클래스에 제네릭을 적용했다면 절대 raw타입으로 사용하지마세요... 무조건 Box<T> T값 지정해줘야함.
        //근데 제네릭이 나오기 전에 로타입으로 작성한 코드가 많아서 raw타입으로 해도 에러는 안나도록 한거지만
        // 앞으로는 제네릭적용한 클래스는 절대 raw타입 선언 금지
    }
}
