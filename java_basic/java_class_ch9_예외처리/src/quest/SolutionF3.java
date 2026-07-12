package quest;

// F-3. 예외별 에러 코드 체계 설계
public class SolutionF3 {

    enum ErrorCode {
        INVALID_INPUT("E1001", "입력값이 올바르지 않습니다"),
        DUPLICATE_EMAIL("E1002", "이미 가입된 이메일입니다"),
        DATA_ACCESS("E5001", "일시적인 시스템 오류입니다");

        private final String code;
        private final String userMessage;

        ErrorCode(String code, String userMessage) {
            this.code = code;
            this.userMessage = userMessage;
        }

        public String getCode() { return code; }
        public String getUserMessage() { return userMessage; }
    }

    // 모든 도메인 예외의 공통 부모: 에러 코드를 강제한다
    static class BusinessException extends RuntimeException {
        private final ErrorCode errorCode;

        public BusinessException(ErrorCode errorCode, String detail) {
            super(detail);
            this.errorCode = errorCode;
        }

        public ErrorCode getErrorCode() { return errorCode; }
    }

    static class DuplicateEmailException2 extends BusinessException {
        public DuplicateEmailException2(String email) {
            super(ErrorCode.DUPLICATE_EMAIL, "email=" + email);
        }
    }

    public static void main(String[] args) {
        try {
            throw new DuplicateEmailException2("dup@test.com");
        } catch (BusinessException e) {
            // 공통 처리: 코드 + 사용자 메시지 + 개발자 상세를 한 곳에서 매핑
            ErrorCode ec = e.getErrorCode();
            System.out.println("code=" + ec.getCode());
            System.out.println("사용자 메시지: " + ec.getUserMessage());
            System.out.println("개발자 상세: " + e.getMessage());
        }
    }
}
// 출력:
// code=E1002
// 사용자 메시지: 이미 가입된 이메일입니다
// 개발자 상세: email=dup@test.com
