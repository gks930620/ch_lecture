package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// B-3. 영향받은 row 수로 성공/실패 판단
// executeUpdate의 반환값(영향받은 행 수)이 곧 성공 판정 기준이다.
public class SolutionB3 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50), age INT)")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO users(id, name, age) VALUES(1, 'Kim', 20)")) {
                ps.executeUpdate();
            }

            System.out.println(deleteUser(conn, 1L));   // 삭제 성공 (id=1)
            System.out.println(deleteUser(conn, 999L)); // 삭제 대상 없음 (id=999)
        }
    }

    static String deleteUser(Connection conn, long id) throws Exception {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            int rows = ps.executeUpdate(); // 영향받은 행 수

            if (rows == 1) {
                return "삭제 성공 (id=" + id + ")";
            } else if (rows == 0) {
                return "삭제 대상 없음 (id=" + id + ")";
            } else {
                return "경고: " + rows + "건 삭제됨 — WHERE 조건 점검 필요";
            }
        }
    }
}
