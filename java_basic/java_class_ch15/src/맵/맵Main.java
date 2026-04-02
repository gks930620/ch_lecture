package 맵;

import java.util.HashMap;
import java.util.Map;

public class 맵Main {
    public static void main(String[] args) {
        Map<String, Member> map=new HashMap<>(); //key가 중복안되는거임.
        map.put("민지",new Member("민지" , 20));
        map.put("하니",new Member("하니" , 20));
        map.put("다니엘",new Member("다니엘" , 19));
        map.put("해린",new Member("해린" , 18));
        map.put("혜인",new Member("혜인" , 16));

        map.put("하니",new Member("팜하니" , 20)); //member하니가사라지고 팜하니member가 들어감
        map.remove("혜인");

        Member 민지 = map.get("민지");
        System.out.println(민지);
        //getOrDefault도 쓰면 좋음
        Member 희진 = map.getOrDefault("희진", new Member("민희진", 45));

        System.out.println(map.keySet());
        System.out.println(map.values());

    }
}
