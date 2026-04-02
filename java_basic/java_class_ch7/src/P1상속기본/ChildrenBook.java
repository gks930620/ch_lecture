package P1상속기본;

public class ChildrenBook
extends Book {
    public boolean checked=false;   // 유해물검사 통과 여부

    @Override
    public void info(){
        System.out.println("제목 :  " + title + " ,  저자 :  " + author + ", 가격 : " + price + ", 유해물검사여부 : " + checked);
    }


}
