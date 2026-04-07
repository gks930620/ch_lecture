package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Q3 {
    record Student(long id, String name, int score) {}

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "1234";
        String sql = "SELECT id, name, score FROM student";

        List<Student> students = new ArrayList<>();
        try (
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                students.add(new Student(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("score")
                ));
            }
            System.out.println(students);
        } catch (SQLException e) {
            System.out.println("조회 실패: " + e.getMessage());
        }
    }
}
