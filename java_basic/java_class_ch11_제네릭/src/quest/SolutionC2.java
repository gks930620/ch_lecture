package quest;

import java.util.List;

// C-2: Comparable 경계로 최솟값/최댓값 유틸 메소드
public class SolutionC2 {
    // Comparable<? super T>: T의 상위 타입에 정의된 compareTo도 허용 (더 유연한 표준 시그니처)
    static <T extends Comparable<? super T>> T min(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("빈 리스트");
        T result = list.get(0);
        for (T item : list) {
            if (item.compareTo(result) < 0) result = item;
        }
        return result;
    }

    static <T extends Comparable<? super T>> T max(List<T> list) {
        if (list.isEmpty()) throw new IllegalArgumentException("빈 리스트");
        T result = list.get(0);
        for (T item : list) {
            if (item.compareTo(result) > 0) result = item;
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> nums = List.of(5, 2, 9, 1, 7);
        System.out.println("최솟값: " + min(nums)); // 최솟값: 1
        System.out.println("최댓값: " + max(nums)); // 최댓값: 9

        List<String> words = List.of("kiwi", "apple", "mango");
        System.out.println("최솟값: " + min(words)); // 최솟값: apple
        System.out.println("최댓값: " + max(words)); // 최댓값: mango
    }
}
