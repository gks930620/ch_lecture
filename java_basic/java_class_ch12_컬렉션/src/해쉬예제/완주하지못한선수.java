package 해쉬예제;

import java.util.HashMap;
import java.util.Map;

// 프로그래머스 "완주하지 못한 선수" 유형 — HashMap으로 개수를 세는 대표 예제.
// 참가자(participant) 중 완주자(completion)에 없는 딱 한 명을 찾는다.
// (동명이인이 있을 수 있으므로 Set이 아니라 "이름 -> 인원수" Map으로 센다.)
public class 완주하지못한선수 {
    public static void main(String[] args) {
        String[] participant = {"leo", "kiki", "eden", "kiki"};
        String[] completion = {"eden", "kiki", "kiki"};   // leo 한 명만 완주 실패

        Map<String, Integer> count = new HashMap<>();

        // 1) 참가자 전원을 이름별로 카운트 +1
        for (String p : participant) {
            count.put(p, count.getOrDefault(p, 0) + 1);
        }

        // 2) 완주자는 카운트 -1
        for (String c : completion) {
            count.put(c, count.get(c) - 1);
        }

        // 3) 카운트가 0이 아닌(=완주 못 한) 사람이 정답
        String answer = "";
        for (Map.Entry<String, Integer> e : count.entrySet()) {
            if (e.getValue() != 0) {
                answer = e.getKey();
                break;
            }
        }

        System.out.println("완주하지 못한 선수: " + answer);  // leo
    }
}
