import java.util.Scanner;

public class P2switch문2 {
    public static void main(String[] args) {
        //입력받은 문자열
        Scanner scanner=new Scanner(System.in);
        String next = scanner.next();
        switch (next){  // switch변수에는 타입 뭐가 오든 상관없음.
            case "민지" :
                System.out.println("킴민지");
                break;
            case "하니" :
                System.out.println("팜");
                break;
            case "다니엘" :
                System.out.println("다니엘 준 마쉬");
                break;
            case  "해린":
                System.out.println("다니엘");
                break;
            case "혜인" :
                System.out.println("이혜인");
                break;
            default:
                System.out.println("뉴진스 몰라요?");
        }

        //굳이 실습하지는 말자... 나만 알고있자..
        switch (next){
//            case "민지","하니" :
//                System.out.println("빵사즈");    case 여러개 쓸수 있음
//                break;
            case "민지","하니"-> {            // : break 대신 ->{} 쓸 수있음.
                System.out.println("빵사즈");
            }
            case "다니엘", "해린"->{
                System.out.println("사탕즈");
            }
            case  "혜인" -> {
                System.out.println("막내");
            }

        }





    }
}
