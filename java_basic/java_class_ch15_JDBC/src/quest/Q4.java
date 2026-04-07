package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Q4 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "1234";
        String withdrawSql = "UPDATE account SET balance = balance - ? WHERE id = ?";
        String depositSql = "UPDATE account SET balance = balance + ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement withdraw = conn.prepareStatement(withdrawSql);
                PreparedStatement deposit = conn.prepareStatement(depositSql)
            ) {
                withdraw.setInt(1, 1000);
                withdraw.setLong(2, 1L);
                withdraw.executeUpdate();

                deposit.setInt(1, 1000);
                deposit.setLong(2, 2L);
                deposit.executeUpdate();

                conn.commit();
                System.out.println("트랜잭션 커밋 완료");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("트랜잭션 롤백: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("연결 실패: " + e.getMessage());
        }
    }
}
