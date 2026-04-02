package 리스트;

import java.util.ArrayList;
import java.util.List;

public class Array리스트메인 {
    public static void main(String[] args) {
        //List부분하고 코드작성하고 Set하고 코드작성하고 ....

        //일반적으로 List에 있는 메소드만 사용함. ArrayList에 있는 메소드 사용하는게 아님.
        // 다만 동작은 ArrayList에서 오버라이딩된 방식으로..
        // 그래서 타입선언은 왠만하면 List<>로 해야 다형성을 지킬 수 있다.
        List<String> list=new ArrayList<>();  //빈 리스트 생성
        if(list.isEmpty()){
            System.out.println("아직은 비어있음.");
        }
        list.add("민지");
        list.add("하니");
        list.add("다니엘");
        list.add("해린");
        list.add("혜인");

        list.set(3,"강해린");
        list.set(4,"이혜인");

        System.out.println("리스트 크기 : " + list.size());

        System.out.println("혜인과 해린을 삭제합니다.");
        list.remove(4);  //혜인삭제
        list.remove("강해린"); //해린삭제


        if(list.contains("민지")){
            System.out.println("민지는 list에 있습니다.");
        }

        for(int i=0; i<list.size() ;i++){
            System.out.println(i+"번째 요소 값 : " + list.get(i));
        }
        System.out.println(list);  //출력하면 원소값 잘 출력됨

    }
}
