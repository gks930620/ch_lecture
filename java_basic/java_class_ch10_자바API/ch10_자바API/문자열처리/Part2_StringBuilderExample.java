package ch10_문자열처리;

public class Part2_StringBuilderExample {
    public static void main(String[] args) {
        System.out.println("=== String vs StringBuilder 성능 비교 ===");

        // String 사용 (느림)
        long start = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += "a";  // 매번 새 객체 생성
        }
        long end = System.currentTimeMillis();
        System.out.println("String: " + (end - start) + "ms");

        // StringBuilder 사용 (빠름)
        start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("a");  // 같은 객체 수정
        }
        end = System.currentTimeMillis();
        System.out.println("StringBuilder: " + (end - start) + "ms");

        System.out.println("\n=== StringBuilder 메소드 ===");
        StringBuilder builder = new StringBuilder();

        // 추가
        builder.append("Hello");
        builder.append(" ");
        builder.append("World");
        System.out.println("append: " + builder);

        // 삽입
        builder.insert(5, ",");
        System.out.println("insert: " + builder);

        // 삭제
        builder.delete(5, 6);
        System.out.println("delete: " + builder);

        // 교체
        builder.replace(0, 5, "Hi");
        System.out.println("replace: " + builder);

        // 역순
        builder.reverse();
        System.out.println("reverse: " + builder);

        // String 변환
        String result = builder.toString();
        System.out.println("toString: " + result);

        System.out.println("\n=== 실용 예제 ===");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM users ");
        sql.append("WHERE age > 20 ");
        sql.append("AND name LIKE '%김%' ");
        sql.append("ORDER BY created_at DESC");

        System.out.println("생성된 SQL:");
        System.out.println(sql.toString());
    }
}

