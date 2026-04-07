package ch2_variable;

public class P3논리 {
    public static void main(String[] args) {
        boolean bunnies=true;   //자바에서는 메모리 최소단위가 1byte라 1byte
        boolean isBig= 3>5 ;    //아직 연산자는 안배웠지만.
        System.out.println(isBig);
        if(bunnies){  //
            System.out.println("bubble gum");
        }else{
            System.out.println("뉴진스에 매력에 빠져보아요");
        }

    }
}
