package quest;

// F-1. 회원 가입 유스케이스 — 예외 분리 설계
// 입력 검증 실패 / 중복 이메일 / DB 실패를 서로 다른 예외 타입으로 분리
public class SolutionF1 {

    static class InvalidSignUpInputException extends RuntimeException {
        public InvalidSignUpInputException(String message) { super(message); }
    }

    static class DuplicateEmailException extends RuntimeException {
        public DuplicateEmailException(String email) { super("이미 가입된 이메일: " + email); }
    }

    static class MemberSaveException extends RuntimeException {
        public MemberSaveException(String message, Throwable cause) { super(message, cause); }
    }

    static class MemberService {
        public void signUp(String email, String password) {
            // 1) 입력 검증 실패
            if (email == null || !email.contains("@")) {
                throw new InvalidSignUpInputException("이메일 형식이 올바르지 않습니다: " + email);
            }
            if (password == null || password.length() < 8) {
                throw new InvalidSignUpInputException("비밀번호는 8자 이상이어야 합니다");
            }
            // 2) 중복 이메일 (조회 시뮬레이션)
            if ("dup@test.com".equals(email)) {
                throw new DuplicateEmailException(email);
            }
            // 3) DB 실패 (저장 시뮬레이션)
            if ("dbfail@test.com".equals(email)) {
                throw new MemberSaveException("회원 저장 실패", new RuntimeException("DB timeout"));
            }
            System.out.println("가입 성공: " + email);
        }
    }

    public static void main(String[] args) {
        MemberService service = new MemberService();
        String[][] inputs = {
                {"kim@test.com", "password123"},
                {"bad-email", "password123"},
                {"dup@test.com", "password123"},
                {"dbfail@test.com", "password123"},
        };
        for (String[] in : inputs) {
            try {
                service.signUp(in[0], in[1]);
            } catch (InvalidSignUpInputException e) {
                System.out.println("[400] 입력 오류: " + e.getMessage());
            } catch (DuplicateEmailException e) {
                System.out.println("[409] " + e.getMessage());
            } catch (MemberSaveException e) {
                System.out.println("[500] 일시적인 오류입니다. (원인: " + e.getCause().getMessage() + ")");
            }
        }
    }
}
// 출력:
// 가입 성공: kim@test.com
// [400] 입력 오류: 이메일 형식이 올바르지 않습니다: bad-email
// [409] 이미 가입된 이메일: dup@test.com
// [500] 일시적인 오류입니다. (원인: DB timeout)
