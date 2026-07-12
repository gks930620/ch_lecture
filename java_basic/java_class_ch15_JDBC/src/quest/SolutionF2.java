package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// F-2. 동적 키워드 검색 (SQL 인젝션 없이)
// 동적 SQL이라도 조립하는 것은 "구조(WHERE 절 유무)"뿐이고, 사용자 입력 값은 전부 ? 바인딩으로 넣는다.
public class SolutionF2 {
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50), age INT)");
                st.executeUpdate("INSERT INTO users VALUES(1,'Kim',20), (2,'Lee',25), (3,'Kimberly',30)");
            }

            System.out.println(search(conn, "Kim", null));  // 이름에 Kim 포함
            System.out.println(search(conn, "Kim", 25));    // 이름 Kim 포함 + 25세 이상
            System.out.println(search(conn, null, null));   // 조건 없음 → 전체
        }
    }

    /** nameKeyword/minAge가 null이면 해당 조건 생략 */
    static List<String> search(Connection conn, String nameKeyword, Integer minAge) throws Exception {
        // SQL 골격은 코드가 조립하되, "값"은 절대 문자열로 잇지 않고 ?로만 넣는다
        StringBuilder sql = new StringBuilder("SELECT id, name, age FROM users WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nameKeyword != null && !nameKeyword.isBlank()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + nameKeyword + "%"); // 와일드카드도 파라미터 값 쪽에 붙인다
        }
        if (minAge != null) {
            sql.append(" AND age >= ?");
            params.add(minAge);
        }
        sql.append(" ORDER BY id");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i)); // 조건 순서와 동일하게 바인딩
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<String> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(rs.getLong("id") + ":" + rs.getString("name")
                            + "(" + rs.getInt("age") + ")");
                }
                return result;
            }
        }
    }
}
