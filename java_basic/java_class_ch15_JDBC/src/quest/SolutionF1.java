package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// F-1. 페이징 조회 API (LIMIT/OFFSET)
// 페이징의 핵심 공식은 OFFSET = (page - 1) * size이며, ORDER BY 없는 페이징은 순서가 뒤섞일 수 있어 정렬 기준이 필수다.
public class SolutionF1 {
    record User(long id, String name) { }

    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50))")) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users VALUES(?, ?)")) {
                for (int i = 1; i <= 25; i++) {
                    ps.setLong(1, i);
                    ps.setString(2, "user-" + i);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            System.out.println(findPage(conn, 1, 10)); // 1페이지: user-1 ~ user-10
            System.out.println(findPage(conn, 3, 10)); // 3페이지: user-21 ~ user-25 (5건)
        }
    }

    /** page는 1부터 시작, size는 페이지당 건수 */
    static List<User> findPage(Connection conn, int page, int size) throws Exception {
        String sql = "SELECT id, name FROM users ORDER BY id LIMIT ? OFFSET ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, size);
            ps.setInt(2, (page - 1) * size); // 건너뛸 행 수
            try (ResultSet rs = ps.executeQuery()) {
                List<User> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new User(rs.getLong("id"), rs.getString("name")));
                }
                return result;
            }
        }
    }
}
