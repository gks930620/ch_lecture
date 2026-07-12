package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// C-2. 중간 실패 시 롤백 확인
// 존재하지 않는 계좌("C")로 입금해 중간 실패를 일으킨다.
// 출금 UPDATE는 이미 실행됐지만 커밋 전이므로 rollback() 한 번에 없던 일이 된다.
public class SolutionC2 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            setUp(conn); // A: 10000원, B: 5000원

            try {
                // 출금(A -3000)은 성공하지만, 입금 대상 "C"가 없어 예외 발생
                transfer(conn, "A", "C", 3000);
            } catch (Exception e) {
                System.out.println("예외 확인: " + e.getMessage());
                // 이체 실패, 롤백: 입금 실패: C
                // 예외 확인: 입금 실패: C
            }

            printBalances(conn);
            // A = 10000   <- 출금까지 취소됨! (롤백 성공)
            // B = 5000
        }
    }

    static void transfer(Connection conn, String from, String to, int amount) throws Exception {
        conn.setAutoCommit(false); // 트랜잭션 시작: 직접 commit/rollback 관리
        try {
            withdraw(conn, from, amount); // 출금
            deposit(conn, to, amount);    // 입금
            conn.commit();                // 둘 다 성공했을 때만 확정
            System.out.println("이체 성공: " + from + " -> " + to + " " + amount + "원");
        } catch (Exception e) {
            conn.rollback();              // 하나라도 실패하면 전부 취소
            System.out.println("이체 실패, 롤백: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(true);     // 커넥션 풀 반환 전 auto-commit 복원
        }
    }

    static void withdraw(Connection conn, String account, int amount) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE accounts SET balance = balance - ? WHERE name = ?")) {
            ps.setInt(1, amount);
            ps.setString(2, account);
            if (ps.executeUpdate() != 1) throw new IllegalStateException("출금 실패: " + account);
        }
    }

    static void deposit(Connection conn, String account, int amount) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE accounts SET balance = balance + ? WHERE name = ?")) {
            ps.setInt(1, amount);
            ps.setString(2, account);
            if (ps.executeUpdate() != 1) throw new IllegalStateException("입금 실패: " + account);
        }
    }

    static void setUp(Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "CREATE TABLE accounts(name VARCHAR(10) PRIMARY KEY, balance INT)")) {
            ps.executeUpdate();
        }
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO accounts VALUES('A', 10000), ('B', 5000)")) {
            ps.executeUpdate();
        }
    }

    static void printBalances(Connection conn) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT name, balance FROM accounts ORDER BY name");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("name") + " = " + rs.getInt("balance"));
            }
        }
    }
}
