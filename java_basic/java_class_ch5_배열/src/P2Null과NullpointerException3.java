public class P2Null과NullpointerException3 {
    public static void main(String[] args) {
        //Nullpointer와  조건문 순서
        String c=null;
//        if(c.isEmpty() && c!=null){   //c.isEmpty()가 먼저 평가되어 바로 NullPointerException 발생
//
//        }
        if( c!=null&&c.isEmpty() ){   //null 검사를 앞에 두면 &&의 단축 평가 덕분에 안전
        }
        System.out.println("null 검사를 먼저 하면 안전하다.");

        // Null 같은 연산도 안됨
        Integer a=null;
        System.out.println(a+3); //에러 (언박싱 과정에서 NullPointerException)

    }
}
