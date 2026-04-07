package P1상속기본;

public class 상속기본Main {
    public static void main(String[] args) {
        //기본,  재정의,  , 타입변화 ,  다형성, 추상클래스
        // 매우 중요

        // 부모메소드 호출,생성자호출,  final,instanceof,  접근제한자, 봉인(seald)
        // 중요

        ChildrenBook childrenBook1=new ChildrenBook();
        childrenBook1.title="흥부와놀부";
        childrenBook1.author="작자미상";
        childrenBook1.price=10000;
        childrenBook1.checked=true;
        childrenBook1.info();

        HorrorBook horrorBook1=new HorrorBook();
        horrorBook1.title="그리고 아무도 없었다.";
        horrorBook1.author="에드거 어쩌구?";
        horrorBook1.price=20000;
        horrorBook1.warningMark=true;
        horrorBook1.info();

        Book book1= new Book();
        book1.title="그냥 책";
        book1.author="한창희";
        book1.price=5000;
        //book1.warningMark=true;
        book1.info();


    }
}
