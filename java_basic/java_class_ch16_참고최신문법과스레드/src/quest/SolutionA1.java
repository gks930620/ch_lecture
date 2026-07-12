package quest;

// A-1. record User(long id, String name) 정의와 사용
// record는 "불변 데이터 묶음"을 한 줄로 선언하는 문법이다. 값이 같으면 equals가 true가 되어 DTO/Map 키로 쓰기 좋다.
public class SolutionA1 {
    record User(long id, String name) { } // 생성자/접근자/equals/hashCode/toString 자동 생성

    public static void main(String[] args) {
        User u1 = new User(1L, "Kim");
        User u2 = new User(1L, "Kim");

        System.out.println(u1.id());        // 1        (getter 대신 컴포넌트 이름 그대로)
        System.out.println(u1.name());      // Kim
        System.out.println(u1);             // User[id=1, name=Kim]  (toString 자동)
        System.out.println(u1.equals(u2));  // true     (값 기반 equals 자동)
    }
}
