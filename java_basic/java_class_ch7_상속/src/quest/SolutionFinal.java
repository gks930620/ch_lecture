package quest;

// F-2. 챌린지 — final로 확장을 제한해야 하는 케이스
public class SolutionFinal {

    static class AuthService {
        // 보안 검증 순서는 자식이 바꾸면 안 된다 -> final 메소드
        final boolean authenticate(String user, String password) {
            if (user == null || password == null) return false;
            return checkPassword(user, password);
        }

        protected boolean checkPassword(String user, String password) {
            return "1234".equals(password); // 예시용
        }
    }

    static class CustomAuthService extends AuthService {
        // authenticate는 final이라 재정의할 수 없다 (주석 해제 시 컴파일 오류)
        // @Override
        // boolean authenticate(String u, String p) { return true; }
        // error: authenticate(...) in CustomAuthService cannot override; overridden method is final
    }

    // 클래스 자체를 확장 금지: 불변 값 객체는 상속으로 불변성이 깨질 수 있다
    static final class ApiKey {
        private final String value;
        ApiKey(String value) { this.value = value; }
        String value() { return value; }
    }
    // static class FakeApiKey extends ApiKey { } // 컴파일 오류! cannot inherit from final ApiKey

    public static void main(String[] args) {
        AuthService auth = new CustomAuthService();
        System.out.println(auth.authenticate("kim", "1234")); // true
        System.out.println(auth.authenticate("kim", "0000")); // false

        ApiKey key = new ApiKey("secret");
        System.out.println(key.value()); // secret
    }
}
