package quest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// D-1 ~ D-3. UserDao(CRUD) + 서비스 계층 + 도메인 예외 변환
// DAO는 SQL/매핑만, 서비스는 검증/유스케이스만 맡도록 나누면 각 계층을 독립적으로 테스트할 수 있다.
public class SolutionD {

    // ---------- 도메인 ----------
    record User(long id, String name, int age) { }

    // D-3. 기술 예외(SQLException)를 감싸는 도메인 예외
    static class DataAccessException extends RuntimeException {
        DataAccessException(String message, Throwable cause) {
            super(message, cause); // 원인 예외 보존 — 로그에서 근본 원인 추적 가능
        }
    }

    // ---------- D-1. DAO 계층: SQL과 매핑만 담당 ----------
    static class UserDao {
        private final Connection conn;

        UserDao(Connection conn) { this.conn = conn; }

        void save(User user) {
            String sql = "INSERT INTO users(id, name, age) VALUES(?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, user.id());
                ps.setString(2, user.name());
                ps.setInt(3, user.age());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("사용자 저장 실패: id=" + user.id(), e);
            }
        }

        Optional<User> findById(long id) {
            String sql = "SELECT id, name, age FROM users WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return Optional.of(mapRow(rs));
                    }
                    return Optional.empty();
                }
            } catch (SQLException e) {
                throw new DataAccessException("사용자 조회 실패: id=" + id, e);
            }
        }

        List<User> findAll() {
            String sql = "SELECT id, name, age FROM users ORDER BY id";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(mapRow(rs));
                }
                return users;
            } catch (SQLException e) {
                throw new DataAccessException("사용자 목록 조회 실패", e);
            }
        }

        int updateAge(long id, int newAge) {
            String sql = "UPDATE users SET age = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, newAge);
                ps.setLong(2, id);
                return ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("사용자 수정 실패: id=" + id, e);
            }
        }

        int deleteById(long id) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                return ps.executeUpdate();
            } catch (SQLException e) {
                throw new DataAccessException("사용자 삭제 실패: id=" + id, e);
            }
        }

        private User mapRow(ResultSet rs) throws SQLException {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getInt("age"));
        }
    }

    // ---------- D-2. 서비스 계층: 비즈니스 규칙 담당 ----------
    static class UserService {
        private final UserDao userDao;

        UserService(UserDao userDao) { this.userDao = userDao; }

        void register(User user) {
            if (user.age() < 0) { // 비즈니스 검증은 서비스 책임
                throw new IllegalArgumentException("나이는 0 이상이어야 합니다");
            }
            userDao.save(user);
        }

        String getUserSummary(long id) {
            return userDao.findById(id)
                    .map(u -> u.name() + "(" + u.age() + "세)")
                    .orElse("미등록 사용자");
        }
    }

    // ---------- 실행 ----------
    // 실행하려면 드라이버 의존성(H2 등)과 DB가 필요하다. (접속 정보는 학습용 플레이스홀더)
    public static void main(String[] args) throws Exception {
        String url = "jdbc:h2:mem:testdb";
        try (Connection conn = DriverManager.getConnection(url, "student", "password1234")) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "CREATE TABLE users(id BIGINT PRIMARY KEY, name VARCHAR(50), age INT)")) {
                ps.executeUpdate();
            }

            UserService service = new UserService(new UserDao(conn));
            service.register(new User(1L, "Kim", 20));
            service.register(new User(2L, "Lee", 25));

            System.out.println(service.getUserSummary(1L));  // Kim(20세)
            System.out.println(service.getUserSummary(99L)); // 미등록 사용자

            // D-3. 도메인 예외 변환 확인: 중복 PK로 저장 시도
            try {
                service.register(new User(1L, "Park", 30));
            } catch (DataAccessException e) {
                System.out.println("도메인 예외: " + e.getMessage());
                // 도메인 예외: 사용자 저장 실패: id=1
                System.out.println("원인: " + e.getCause().getClass().getSimpleName());
                // 원인: JdbcSQLIntegrityConstraintViolationException (H2 기준)
            }
        }
    }
}
