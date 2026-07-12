package quest;

import java.util.List;
import java.util.function.Function;

// F-1: 체크 예외를 다루는 람다 래퍼 유틸
public class SolutionF1 {
    @FunctionalInterface
    interface ThrowingFunction<T, R> {
        R apply(T t) throws Exception;
    }

    static class Unchecked {
        // checked 예외를 RuntimeException으로 감싸 표준 Function으로 변환
        static <T, R> Function<T, R> wrap(ThrowingFunction<T, R> f) {
            return t -> {
                try {
                    return f.apply(t);
                } catch (RuntimeException e) {
                    throw e; // 이미 unchecked면 그대로
                } catch (Exception e) {
                    throw new IllegalStateException("람다 실행 실패: " + t, e);
                }
            };
        }
    }

    // checked 예외를 던지는 기존 메소드라고 가정
    static byte[] readBytes(String path) throws java.io.IOException {
        if (path.isBlank()) throw new java.io.IOException("잘못된 경로");
        return path.getBytes(); // 예제 단순화
    }

    public static void main(String[] args) {
        List<String> paths = List.of("a.txt", "b.txt");

        // wrap 덕분에 map에 checked 예외 메소드를 그대로 전달 가능
        List<Integer> sizes = paths.stream()
                .map(Unchecked.wrap(SolutionF1::readBytes))
                .map(bytes -> bytes.length)
                .toList();
        System.out.println(sizes); // [5, 5]

        // 예외 발생 케이스: IOException이 IllegalStateException으로 래핑됨
        try {
            List.of("  ").stream().map(Unchecked.wrap(SolutionF1::readBytes)).toList();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());                          // 람다 실행 실패:
            System.out.println(e.getCause().getClass().getSimpleName()); // IOException
        }
    }
}
