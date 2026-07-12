package quest;

import java.util.ArrayList;
import java.util.HashMap;

// B-1. 명시 타입 코드를 var로 리팩터링
// var는 우변(생성자, 리터럴)에 타입 정보가 이미 드러나 있을 때 중복을 없애 주는 도구다.
public class SolutionB1 {
    public static void main(String[] args) {
        // 리팩터링 전: 좌우에 타입이 중복됨
        // HashMap<String, ArrayList<Integer>> scoresByName = new HashMap<String, ArrayList<Integer>>();
        // String message = "hello";

        // 리팩터링 후: 우변만 봐도 타입이 명확하므로 var가 적합
        var scoresByName = new HashMap<String, ArrayList<Integer>>();
        var message = "hello";
        var count = 10;

        scoresByName.put("Kim", new ArrayList<>());
        scoresByName.get("Kim").add(90);

        System.out.println(message + " / " + count); // hello / 10
        System.out.println(scoresByName);            // {Kim=[90]}
    }
}
