package ch2_variable;

public class P3논리 {
    public static void main(String[] args) {
        boolean bunnies=true;   //boolean의 크기는 JVM 구현에 따라 다르다
        boolean isBig= 3>5 ;    //아직 연산자는 안배웠지만.
        System.out.println(isBig);
        if(bunnies){  //
            System.out.println("bubble gum");
        }else{
            System.out.println("뉴진스의 매력에 빠져보아요");
        }

    }
}
