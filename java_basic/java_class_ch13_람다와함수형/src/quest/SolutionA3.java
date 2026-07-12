package quest;

import java.util.Comparator;

// A-3: 익명 클래스 vs 람다 비교
public class SolutionA3 {
    public static void main(String[] args) {
        // 1) 익명 클래스: 타입/메소드 선언이 반복됨
        Comparator<String> anon = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        };

        // 2) 람다: 본문만 남음
        Comparator<String> lambda = (a, b) -> a.length() - b.length();

        System.out.println(anon.compare("abc", "a"));   // 2
        System.out.println(lambda.compare("abc", "a")); // 2

        // 차이 요약
        // - 익명 클래스: 자체 this(익명 클래스 인스턴스), 새 스코프, 클래스 파일 생성
        // - 람다: this가 바깥 클래스, 스코프 공유, invokedynamic 기반 (더 가벼움)
    }
}
