package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// E-2. executeBatch로 대량 INSERT
// 1만 번의 개별 executeUpdate는 왕복 통신이 1만 번이지만, 배치는 모아서 한 번에 보낸다.
// 적당한 크기(수백~수천 건)로 끊어 실행하고 전체를 하나의 트랜잭션으로 묶는 것이 정석이다.
public class SolutionE2 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50))")) {
                ps.executeUpdate();
            }

            String sql = "INSERT INTO users(id, name) VALUES(?, ?)";
            conn.setAutoCommit(false); // 배치는 트랜잭션으로 묶는 것이 일반적
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                int total = 0;
                for (int i = 1; i <= 10_000; i++) {
                    ps.setLong(1, i);
                    ps.setString(2, "user-" + i);
                    ps.addBatch();            // 즉시 실행하지 않고 모아 둠

                    if (i % 1000 == 0) {      // 1000건 단위로 끊어서 전송 (메모리 보호)
                        total += ps.executeBatch().length;
                        ps.clearBatch();
                    }
                }
                total += ps.executeBatch().length; // 남은 건 처리 (여기서는 0건)
                conn.commit();
                System.out.println("총 삽입: " + total + "건"); // 총 삽입: 10000건
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
