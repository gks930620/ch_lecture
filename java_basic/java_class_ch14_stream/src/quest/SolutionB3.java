package quest;

import java.util.Comparator;
import java.util.List;

// B-3. sorted + Comparator로 객체 정렬
// 키 추출 함수만 넘기면 되는 Comparator.comparing을 쓰고, thenComparing으로 2차 정렬 기준을 연결한다.
public class SolutionB3 {
    record Member(String name, int age) {}

    public static void main(String[] args) {
        List<Member> members = List.of(
                new Member("kim", 30),
                new Member("lee", 25),
                new Member("ahn", 30),
                new Member("park", 28)
        );

        List<Member> sorted = members.stream()
                .sorted(Comparator.comparingInt(Member::age).reversed()
                        .thenComparing(Member::name)) // 나이 내림차순, 같으면 이름 오름차순
                .toList();

        sorted.forEach(m -> System.out.println(m.name() + " " + m.age()));
        // ahn 30
        // kim 30
        // park 28
        // lee 25
    }
}
