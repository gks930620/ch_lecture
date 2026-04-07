import java.util.*;

public class P4String2 {
    public static void main(String[] args) {
        //어떤 메소드를 쓸 때
        //객체를 변경하는것 vs 객체는 유지하면서 새로운값을 return
        // 그래도 책에는 변경불가(Immutable)한 특성이 있다고 함
        //추출과 잘라내기 ,
        String birth = "040507";
        char mz = birth.charAt(0);
        String month = birth.substring(2, 4);   //include, exclude 화인
        if (mz == '0') {
            System.out.println("아직까지는 형소리 듣는게 가능해요.");
        } else {
            System.out.println("10대는 우리를 아저씨,할아버지라고 부르기로 했어요");
        }

        //길이
        String ditto = "ditto ditto ditto";
        System.out.println(ditto.length());  //물론 \n, 빈칸 등에 대한것도 길이에 포함

        //문자열 대체   replace,replaceFisrt, replaceAll
        System.out.println(ditto);

        //문자열 찾기   index, last index
        int startStrDitto = ditto.indexOf("ditto");
        int lastStrDitto = ditto.lastIndexOf("ditto");           //d의 위치 . 없으면 -1
        System.out.println(startStrDitto);
        System.out.println(lastStrDitto);

        //문자열분리
        String[] dittos = ditto.split("ditto");
        System.out.println("ditto 개수 : " + (dittos.length-1));
        int count = 0;
        int dittoIndex = ditto.indexOf("ditto");
        while (dittoIndex != -1) {
            ditto = ditto.replaceFirst("ditto", "");
            count++;
            dittoIndex = ditto.indexOf("ditto");
        }
        System.out.println("ditto 개수 : " + count);

        //문자열 분리
        String memberNames = "민지 하니 다니엘 해린 혜인";
        String[] memberNameArray = memberNames.split(" ");
        for (int i = 0; i < memberNameArray.length; i++) {
            System.out.println(memberNameArray[i]);
        }
    }

}
