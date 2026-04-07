package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Q2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "1234";
        String sql = "INSERT INTO student(name, score) VALUES(?, ?)";

        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, "Kim");
            pstmt.setInt(2, 95);
            int rows = pstmt.executeUpdate();
            System.out.println("삽입 행 수: " + rows);
        } catch (SQLException e) {
            System.out.println("실행 실패: " + e.getMessage());
        }
    }
}
