package quest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// F-1 ~ F-3: 제네릭 캐시 Cache<K, V> + 만료 정책 인터페이스 분리 + 타입 안전성 테스트
public class SolutionF {

    // F-2: 만료 정책을 제네릭 인터페이스로 분리 - 캐시 본체와 정책이 독립적으로 교체 가능
    interface ExpirationPolicy<V> {
        boolean isExpired(V value, long storedAtMillis);
    }

    // 정책 구현 1: 절대 만료 없음
    static class NoExpiration<V> implements ExpirationPolicy<V> {
        @Override
        public boolean isExpired(V value, long storedAtMillis) {
            return false;
        }
    }

    // 정책 구현 2: TTL(저장 후 일정 시간) 만료
    static class TtlExpiration<V> implements ExpirationPolicy<V> {
        private final long ttlMillis;

        TtlExpiration(long ttlMillis) { this.ttlMillis = ttlMillis; }

        @Override
        public boolean isExpired(V value, long storedAtMillis) {
            return System.currentTimeMillis() - storedAtMillis > ttlMillis;
        }
    }

    // F-1: 제네릭 캐시 - K는 키 타입, V는 값 타입 (관례적 이름 K, V 사용)
    static class Cache<K, V> {
        private record Entry<V>(V value, long storedAtMillis) {}

        private final Map<K, Entry<V>> store = new HashMap<>();
        private final ExpirationPolicy<V> policy;

        Cache(ExpirationPolicy<V> policy) { this.policy = policy; }

        public void put(K key, V value) {
            store.put(key, new Entry<>(value, System.currentTimeMillis()));
        }

        public Optional<V> get(K key) {
            Entry<V> entry = store.get(key);
            if (entry == null) return Optional.empty();
            if (policy.isExpired(entry.value(), entry.storedAtMillis())) {
                store.remove(key); // 만료된 항목은 제거
                return Optional.empty();
            }
            return Optional.of(entry.value());
        }

        public int size() { return store.size(); }
    }

    public static void main(String[] args) {
        Cache<String, Integer> scoreCache = new Cache<>(new NoExpiration<>());
        scoreCache.put("kim", 95);
        scoreCache.put("lee", 88);

        System.out.println(scoreCache.get("kim").orElse(-1));  // 95
        System.out.println(scoreCache.get("none").orElse(-1)); // -1 (미존재)
        System.out.println("크기: " + scoreCache.size());       // 크기: 2

        // TTL 0ms 정책 -> 저장 직후에도 만료 처리되는지 확인
        Cache<String, String> ttlCache = new Cache<>(new TtlExpiration<>(0));
        ttlCache.put("token", "abc123");
        try { Thread.sleep(5); } catch (InterruptedException ignored) {}
        System.out.println(ttlCache.get("token").orElse("만료됨")); // 만료됨

        // F-3: 잘못된 타입 사용은 아래처럼 전부 컴파일 오류 - 주석을 해제하면 빌드가 실패한다.
        // cache.put(123, 95);              // 키 자리에 Integer 불가 (K=String으로 확정됨)
        // cache.put("lee", "88점");         // 값 자리에 String 불가 (V=Integer로 확정됨)
        // String score = scoreCache.get("kim").orElse(-1); // 반환은 Integer라 String에 대입 불가
        // Cache<String, Integer> o = new Cache<String, String>(new NoExpiration<>()); // 불공변
        int score = scoreCache.get("kim").orElse(-1); // 올바른 사용: 캐스팅 없이 Integer로 받음
        System.out.println("kim 점수: " + score);       // kim 점수: 95
    }
}
