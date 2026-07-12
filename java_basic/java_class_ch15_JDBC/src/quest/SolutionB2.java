package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// B-2. UPDATE / DELETE 구현
// UPDATE/DELETE에서 WHERE 조건 누락은 전체 행을 바꾸는 대형 사고로 이어진다. 조건 컬럼도 반드시 ? 바인딩으로 처리한다.
public class SolutionB2 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb"; // 학습용 인메모리 DB (플레이스홀더)
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50), age INT)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(id, name, age) VALUES(1, 'Kim', 20), (2, 'Lee', 25)")) {
                ps.executeUpdate();
            }

            System.out.println("UPDATE: " + updateAge(conn, 1L, 21)); // UPDATE: 1
            System.out.println("DELETE: " + deleteById(conn, 2L));    // DELETE: 1
        }
    }

    static int updateAge(Connection conn, long id, int newAge) throws Exception {
        String sql = "UPDATE users SET age = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newAge);
            ps.setLong(2, id);
            return ps.executeUpdate();
        }
    }

    static int deleteById(Connection conn, long id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }
}
