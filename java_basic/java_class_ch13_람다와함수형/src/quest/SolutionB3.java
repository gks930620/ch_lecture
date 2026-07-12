package quest;

import java.util.function.Consumer;

// B-3: Consumer<String>로 로그 출력 함수 (값을 소비하고 반환이 없는 동작)
public class SolutionB3 {
    public static void main(String[] args) {
        Consumer<String> log = msg -> System.out.println("[LOG] " + msg);
        log.accept("서버 시작");     // [LOG] 서버 시작
        log.accept("요청 처리 완료"); // [LOG] 요청 처리 완료
    }
}
