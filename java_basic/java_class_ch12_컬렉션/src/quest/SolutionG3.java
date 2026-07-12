package quest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// G-3: 멀티스레드 환경에서 ConcurrentHashMap으로 안전 집계 (멀티스레드는 ch16 내용)
public class SolutionG3 {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> counter = new ConcurrentHashMap<>();
        List<String> keys = List.of("apple", "banana", "apple");

        ExecutorService pool = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1000; i++) { // 1000개 작업, 각 작업이 keys의 단어를 1번씩 집계
            pool.submit(() -> {
                for (String key : keys) {
                    counter.merge(key, 1, Integer::sum); // merge는 원자적으로 동작
                }
            });
        }
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("apple  = " + counter.get("apple"));  // apple  = 2000 (항상 정확)
        System.out.println("banana = " + counter.get("banana")); // banana = 1000 (항상 정확)

        // 핵심: ConcurrentHashMap의 merge/compute는 키 단위로 원자적으로 실행되어 집계가 항상 정확하다.
    }
}
