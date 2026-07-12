package quest;

// B-2. var가 가독성을 해치는 예와 명시 타입 복원
// var의 판단 기준은 "우변만 보고 타입을 알 수 있는가"이다. 메소드 반환값처럼 타입이 드러나지 않는 곳엔 명시 타입을 쓴다.
public class SolutionB2 {
    public static void main(String[] args) {
        // 나쁜 예: 우변이 메소드 호출이라 반환 타입을 코드만 봐서는 알 수 없음
        var data = loadData();      // ??? 무슨 타입이지? List? Map? String?
        var result = process(data); // 연쇄되면 추적이 더 어려워짐

        // 수정: 메소드 반환값을 받을 때는 명시 타입으로 의도를 드러낸다
        java.util.List<String> names = loadData();
        int total = process(names);

        System.out.println(names);  // [Kim, Lee]
        System.out.println(total);  // 2
        System.out.println(data + " / " + result); // [Kim, Lee] / 2
    }

    static java.util.List<String> loadData() {
        return java.util.List.of("Kim", "Lee");
    }

    static int process(java.util.List<String> list) {
        return list.size();
    }
}
