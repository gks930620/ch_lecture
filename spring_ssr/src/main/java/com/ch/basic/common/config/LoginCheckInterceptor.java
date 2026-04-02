package com.ch.basic.common.config;

import com.ch.basic.user.dto.LoginUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 여부를 체크하는 Interceptor
 *
 * Interceptor: Controller 실행 전/후에 공통 로직을 실행하는 Spring MVC 기능
 *   - Filter(서블릿 레벨)보다 안쪽, Controller 바로 앞에서 동작
 *   - preHandle(): Controller 실행 전에 호출 → false 반환 시 Controller 실행 안 함
 *
 * ※ 기존에는 각 Controller 메서드에서 직접 session null 체크를 했지만,
 *    Interceptor로 분리하여 공통 처리 (코드 중복 제거)
 *
 * ※ 이 Interceptor가 동작하는 URL은 WebConfig.addInterceptors()에서 설정:
 *   - addPathPatterns(): 로그인 체크 대상 URL
 *     → /community/write, /community/{id}/edit, /community/{id}/delete, /mypage
 *   - excludePathPatterns(): 비로그인도 접근 가능한 URL
 *     → /login, /signup, /, /community, /community/{id}, /uploads/**, /css/**, /js/**
 *   - 댓글 API(/api/**)는 여기서 등록하지 않고 CommentApiController에서 자체 세션 체크
 *
 * @Component: Spring Bean으로 등록 (WebConfig에서 주입받아 사용)
 */
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    /**
     * Controller 실행 전에 호출되는 메서드
     *
     * @param request  HTTP 요청 정보 (URL, 파라미터, 세션 등)
     * @param response HTTP 응답 정보 (리다이렉트 등에 사용)
     * @param handler  실행될 Controller 메서드 정보
     * @return true → Controller 실행 진행 / false → Controller 실행 중단
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 1) 세션 존재 여부 확인
        // request.getSession(false): 기존 세션이 있으면 반환, 없으면 null (새로 생성 안 함)
        // cf. getSession(true) 또는 getSession(): 세션 없으면 새로 생성 → 불필요한 세션 생성 방지를 위해 false
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 세션 자체가 없음 → 한 번도 로그인한 적 없거나, 세션 만료
            response.sendRedirect("/login");
            return false;
        }

        // 2) 세션에 로그인 정보가 있는지 확인
        LoginUserDTO loginUser = (LoginUserDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            // 세션은 있지만 로그인 정보가 없음 → 로그아웃 후 세션이 남아있는 경우 등
            response.sendRedirect("/login");
            return false;
        }

        // 로그인 확인 완료 → Controller 실행 계속 진행
        return true;
    }
}
