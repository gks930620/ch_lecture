package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Q1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("연결 성공: " + !conn.isClosed());
        } catch (SQLException e) {
            System.out.println("연결 실패: " + e.getMessage());
        }
    }
}
