package P1상속기본;

public class Book {
    public String title;
    public String author;
    public int price;

    public  void info(){
        System.out.println("이 책의 이름은 " +this.title + ", 저자는  " + author
         + " ,  가격은 " + price + "입니다.");
    }

}
