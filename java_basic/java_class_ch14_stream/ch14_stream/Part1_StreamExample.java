package ch13_StreamAPI;

import java.util.*;
import java.util.stream.*;

public class Part1_StreamExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        System.out.println("=== filter (짝수만) ===");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .forEach(System.out::println);

        System.out.println("\n=== map (제곱) ===");
        numbers.stream()
            .map(n -> n * n)
            .limit(5)
            .forEach(System.out::println);

        System.out.println("\n=== filter + map ===");
        numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2)
            .forEach(System.out::println);

        System.out.println("\n=== sorted ===");
        List<String> names = Arrays.asList("홍길동", "김철수", "이영희", "박민수");
        names.stream()
            .sorted()
            .forEach(System.out::println);

        System.out.println("\n=== distinct (중복 제거) ===");
        List<Integer> duplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5);
        duplicates.stream()
            .distinct()
            .forEach(System.out::println);

        System.out.println("\n=== collect (List로 수집) ===");
        List<Integer> evenNumbers = numbers.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println(evenNumbers);

        System.out.println("\n=== reduce (합계) ===");
        int sum = numbers.stream()
            .reduce(0, (a, b) -> a + b);
        System.out.println("합계: " + sum);

        int product = numbers.stream()
            .limit(5)
            .reduce(1, (a, b) -> a * b);
        System.out.println("1~5 곱: " + product);

        System.out.println("\n=== count, min, max ===");
        long count = numbers.stream().count();
        System.out.println("개수: " + count);

        Optional<Integer> min = numbers.stream().min(Integer::compare);
        Optional<Integer> max = numbers.stream().max(Integer::compare);
        System.out.println("최솟값: " + min.orElse(0));
        System.out.println("최댓값: " + max.orElse(0));

        System.out.println("\n=== anyMatch, allMatch ===");
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("짝수 있음? " + hasEven);
        System.out.println("모두 양수? " + allPositive);

        System.out.println("\n=== 복합 예제 ===");
        double average = numbers.stream()
            .filter(n -> n % 2 == 0)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
        System.out.println("짝수 평균: " + average);
    }
}

