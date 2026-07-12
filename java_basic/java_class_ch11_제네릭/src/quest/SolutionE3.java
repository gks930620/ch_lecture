package quest;

import java.util.ArrayList;
import java.util.List;

// E-3: instanceof List<String>이 불가능한 이유
public class SolutionE3 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Kim");

        // if (names instanceof List<String>) { } // 컴파일 오류: 소거로 런타임에 검사 불가
        Object obj = names;                         // 정적 타입을 넓혀 런타임 검사의 의미를 살림
        if (obj instanceof List<?> list) {          // 와일드카드 형태는 허용 (Java 16+ 패턴 매칭)
            System.out.println("List 맞음, 크기: " + list.size());
        }

        // 이유: 타입 소거 때문에 런타임 객체에는 List<String>인지 List<Integer>인지 정보가 없다.
        // 둘 다 그냥 ArrayList 객체일 뿐이라 JVM이 답할 수 없어 컴파일러가 막는다.
        // 타입 인자를 묻지 않는 instanceof List<?>(또는 raw List)만 허용된다.
    }
}
