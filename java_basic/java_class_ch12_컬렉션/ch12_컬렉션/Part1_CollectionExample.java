package ch8_컬렉션프레임워크;

import java.util.*;

public class Part1_CollectionExample {
    public static void main(String[] args) {
        // ===== List =====
        System.out.println("=== ArrayList ===");
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // 중복 허용

        System.out.println("크기: " + fruits.size());
        System.out.println("2번째: " + fruits.get(1));
        System.out.println("전체: " + fruits);

        // ===== Set =====
        System.out.println("\n=== HashSet ===");
        HashSet<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple");  // 중복 무시됨

        System.out.println("크기: " + set.size());  // 3
        System.out.println("전체: " + set);
        System.out.println("Apple 포함? " + set.contains("Apple"));

        // ===== Map =====
        System.out.println("\n=== HashMap ===");
        HashMap<String, Integer> priceMap = new HashMap<>();
        priceMap.put("Apple", 1000);
        priceMap.put("Banana", 2000);
        priceMap.put("Cherry", 3000);

        System.out.println("Apple 가격: " + priceMap.get("Apple"));
        System.out.println("전체: " + priceMap);

        // Map 순회
        System.out.println("\nMap 순회:");
        for (Map.Entry<String, Integer> entry : priceMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "원");
        }

        // ===== TreeSet (정렬됨) =====
        System.out.println("\n=== TreeSet (정렬) ===");
        TreeSet<Integer> numbers = new TreeSet<>();
        numbers.add(5);
        numbers.add(2);
        numbers.add(8);
        numbers.add(1);
        System.out.println(numbers);  // [1, 2, 5, 8] 자동 정렬

        // ===== Collections 유틸리티 =====
        System.out.println("\n=== Collections 유틸리티 ===");
        List<Integer> nums = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9));
        System.out.println("원본: " + nums);

        Collections.sort(nums);
        System.out.println("정렬: " + nums);

        Collections.reverse(nums);
        System.out.println("역순: " + nums);

        System.out.println("최댓값: " + Collections.max(nums));
        System.out.println("최솟값: " + Collections.min(nums));
    }
}

