package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// C-3. auto-commit on/off 차이 실험
// auto-commit ON에서는 SQL 한 문장이 각각 즉시 커밋되므로 롤백할 대상이 없다.
// 여러 SQL을 원자적으로 묶으려면 반드시 OFF로 전환해야 한다.
public class SolutionC3 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE logs(msg VARCHAR(50))")) {
                ps.executeUpdate();
            }

            // 실험 1: auto-commit ON (기본값) — SQL 실행 즉시 확정
            insert(conn, "auto-commit-on");
            conn.rollback(); // 이미 커밋되어 있어 아무 효과 없음 (H2는 무시)
            System.out.println("실험1 행 수: " + count(conn)); // 실험1 행 수: 1

            // 실험 2: auto-commit OFF — commit 전까지 미확정
            conn.setAutoCommit(false);
            insert(conn, "auto-commit-off");
            conn.rollback(); // commit 전이므로 INSERT가 취소됨
            conn.setAutoCommit(true);
            System.out.println("실험2 행 수: " + count(conn)); // 실험2 행 수: 1 (그대로)
        }
    }

    static void insert(Connection conn, String msg) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO logs VALUES(?)")) {
            ps.setString(1, msg);
            ps.executeUpdate();
        }
    }

    static int count(Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM logs");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
