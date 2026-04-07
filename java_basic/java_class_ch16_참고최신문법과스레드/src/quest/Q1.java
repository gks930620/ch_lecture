package quest;

public class Q1 {
    record User(String name, String role) {}

    static String roleMessage(User user) {
        return switch (user.role()) {
            case "ADMIN" -> "관리자 권한";
            case "USER" -> "일반 사용자";
            default -> "게스트";
        };
    }

    public static void main(String[] args) {
        User user = new User("Kim", "ADMIN");
        System.out.println(roleMessage(user));
    }
}
