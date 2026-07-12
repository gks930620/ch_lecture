package quest;

import java.util.LinkedHashMap;
import java.util.Map;

// G-2: 최근 조회 순서를 유지하는 LRU 캐시 (LinkedHashMap)
public class SolutionG2 {
    static class LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        LruCache(int capacity) {
            // accessOrder=true: get/put 할 때마다 해당 항목을 맨 뒤(최근)로 이동
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity; // 용량 초과 시 가장 오래 사용 안 한 항목 자동 제거
        }
    }

    public static void main(String[] args) {
        LruCache<String, Integer> cache = new LruCache<>(3);
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);
        System.out.println(cache.keySet()); // [A, B, C]

        cache.get("A"); // A를 조회 -> A가 "최근 사용"으로 이동
        System.out.println(cache.keySet()); // [B, C, A]

        cache.put("D", 4); // 용량 초과 -> 가장 오래 사용 안 한 B가 제거됨
        System.out.println(cache.keySet()); // [C, A, D]
        System.out.println(cache.containsKey("B")); // false
    }
}
