package quest;

import java.sql.Connection;
import java.sql.DriverManager;

// E-3. try-with-resources 누락 시 리소스 누수 재현
// Connection은 DB 서버의 한정 자원이라 닫지 않으면 애플리케이션이 아니라 DB 전체가 마비된다.
public class SolutionE3 {
    // 실행하려면 드라이버 의존성과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    // 주의: 이 데모는 일부러 잘못된 코드를 보여 준다. 서버 DB(최대 연결 수 제한)에서
    //       이렇게 연결을 만들면 "Too many connections" 오류로 서비스 전체가 멈춘다.
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";

        // 1) 나쁜 코드: close() 없이 연결을 계속 생성 — 누수!
        for (int i = 1; i <= 5; i++) {
            Connection conn = DriverManager.getConnection(url, "student", "password1234");
            System.out.println("연결 " + i + " 생성 (닫지 않음!)");
            // conn.close() 누락 — GC가 언제 거둘지 보장 없음.
            // MySQL 등 서버 DB라면 max_connections에 도달하는 순간
            // 이후 모든 요청이 SQLException으로 실패한다.
        }

        // 2) 올바른 코드: try-with-resources — 블록을 벗어나면 무조건 close()
        for (int i = 1; i <= 5; i++) {
            try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
                System.out.println("연결 " + i + " 사용 후 자동 반납");
            } // 예외가 나도 여기서 반드시 닫힌다
        }
    }
}
