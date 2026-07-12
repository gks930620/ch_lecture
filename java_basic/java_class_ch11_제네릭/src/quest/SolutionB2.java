package quest;

// B-2: 두 값을 받아 더 큰 값을 반환하는 제네릭 메소드
public class SolutionB2 {
    // T extends Comparable<T>: "자기 자신과 비교 가능한 타입"만 허용
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static void main(String[] args) {
        System.out.println(max(3, 7));            // 7
        System.out.println(max("apple", "kiwi")); // kiwi (사전순 비교)
        System.out.println(max(2.5, 1.9));        // 2.5

        // max(new Object(), new Object()); // 컴파일 오류: Object는 Comparable이 아님
        // 경계가 없으면 T에서 compareTo를 호출할 수 없다(컴파일러는 T를 Object로만 취급).
    }
}
