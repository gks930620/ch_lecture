package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// E-1. 문자열 연결 SQL vs 바인딩 SQL (SQL 인젝션)
// 문자열 연결은 사용자 입력이 SQL 문법으로 실행될 통로를 연다. 바인딩(? + setXxx)은 입력을 항상 데이터로만 취급한다.
public class SolutionE1 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50))");
                st.executeUpdate("INSERT INTO users VALUES(1, 'Kim'), (2, 'Lee')");
            }

            // 악의적 입력: 항상 참이 되는 조건을 주입
            String evilInput = "nobody' OR '1'='1";

            // 1) 나쁜 방식: 문자열 연결 — 입력이 SQL 문법으로 해석됨
            String badSql = "SELECT COUNT(*) FROM users WHERE name = '" + evilInput + "'";
            // 실제 실행되는 SQL: SELECT COUNT(*) FROM users WHERE name = 'nobody' OR '1'='1'
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(badSql)) {
                rs.next();
                System.out.println("문자열 연결: " + rs.getInt(1) + "건 노출");
                // 문자열 연결: 2건 노출  <- 전체 데이터가 뚫림!
            }

            // 2) 올바른 방식: 바인딩 — 입력 전체가 하나의 "값"으로만 취급됨
            String goodSql = "SELECT COUNT(*) FROM users WHERE name = ?";
            try (PreparedStatement ps = conn.prepareStatement(goodSql)) {
                ps.setString(1, evilInput);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    System.out.println("바인딩: " + rs.getInt(1) + "건");
                    // 바인딩: 0건  <- "nobody' OR '1'='1"이라는 이름은 없으므로 안전
                }
            }
        }
    }
}
