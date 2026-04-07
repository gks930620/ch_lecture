package quest;

import java.util.HashMap;
import java.util.Map;

public class Q3 {
    public static void main(String[] args) {
        String sentence = "java is fun and java is powerful";
        String[] words = sentence.split(" ");

        Map<String, Integer> countMap = new HashMap<>();
        for (String word : words) {
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }

        System.out.println(countMap);
    }
}
