package P1상속기본;

public class HorrorBook extends Book {
    public boolean warningMark;
    //경고마크여부

    //부모클래스에 있는 info를 자식인 HorrorBook클래스가 다시 정의함
    //다시 정의하면 info메소드를 사용했을 때 부모클래스의 info가 아닌 여기서 만든(재정의한) info가 실행됩니다.
    @Override
    public void info(){
        System.out.println("제목 :  " + title + " ,  저자 :  " + author + ", 가격 : " + price + ", 경고마크여부 : " + warningMark);
    }

}
