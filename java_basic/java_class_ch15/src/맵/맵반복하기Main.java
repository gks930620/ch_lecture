package 맵;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class 맵반복하기Main {
    public static void main(String[] args) {
        Map<String, Member> map=new HashMap<>(); //key가 중복안되는거임.
        map.put("민지",new Member("민지" , 20));
        map.put("하니",new Member("하니" , 20));
        map.put("다니엘",new Member("다니엘" , 19));
        map.put("해린",new Member("해린" , 18));
        map.put("혜인",new Member("혜인" , 16));

        Set<String> keySet = map.keySet();  //set으로 반복하면 되니까 iterator로도 가능.
        for(String  key : keySet) {
            System.out.println(map.get(key));
        }



    }
}
