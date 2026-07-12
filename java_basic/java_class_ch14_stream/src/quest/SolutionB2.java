package quest;

import java.util.List;

// B-2. 2차원 리스트를 flatMap으로 평탄화
// map(List::stream)이면 Stream<Stream<String>>이 되므로, 한 겹 풀어주는 flatMap이 필요하다.
public class SolutionB2 {
    public static void main(String[] args) {
        List<List<String>> nested = List.of(
                List.of("a", "b"),
                List.of("c"),
                List.of("d", "e", "f")
        );

        List<String> flat = nested.stream()
                .flatMap(List::stream)
                .toList();

        System.out.println(flat); // [a, b, c, d, e, f]
    }
}
