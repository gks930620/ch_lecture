import java.util.Arrays;

public class P2Null과NullpointerException3 {
    public static void main(String[] args) {
        //Nullpointer와  조건문 순서
        String c=null;
        if(c.isEmpty() && c!=null){

        }
        if( c!=null&&c.isEmpty() ){

        }

        // Null 같은 연산도 안됨
        Integer a=null;
        System.out.println(a+3); //에러

    }
}
