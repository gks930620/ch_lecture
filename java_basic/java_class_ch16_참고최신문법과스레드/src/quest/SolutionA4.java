package quest;

// A-4. 텍스트 블록으로 멀티라인 SQL 작성
// 텍스트 블록은 줄바꿈과 따옴표를 이스케이프 없이 그대로 쓸 수 있어 SQL/JSON/HTML에 특히 유용하다.
public class SolutionA4 {
    public static void main(String[] args) {
        // 기존 방식: + 연결과 \n 이스케이프로 지저분함
        String oldSql = "SELECT id, name\n"
                + "FROM users\n"
                + "WHERE status = 'ACTIVE'";

        // 텍스트 블록: 눈에 보이는 그대로가 문자열이 됨
        String sql = """
                SELECT id, name
                FROM users
                WHERE status = 'ACTIVE'
                ORDER BY id
                """; // 닫는 """의 들여쓰기 위치가 공통 들여쓰기 제거 기준

        System.out.println(oldSql);
        System.out.println("---");
        System.out.println(sql);
        // SELECT id, name
        // FROM users
        // WHERE status = 'ACTIVE'
        // ORDER BY id
    }
}
