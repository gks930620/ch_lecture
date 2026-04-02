package com.example.chlecture.cookiesession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 05. 쿠키/세션 실용 예제 서블릿
 *
 * 담당 URL:
 *   POST /05_cookie_session/login    — 세션 기반 로그인
 *   POST /05_cookie_session/logout   — 세션 무효화 (로그아웃)
 *   POST /05_cookie_session/notice   — "1주일간 공지 안보기" 쿠키 처리
 */
@WebServlet(urlPatterns = {
        "/05_cookie_session/login",
        "/05_cookie_session/logout",
        "/05_cookie_session/notice"
})
public class LoginServlet extends HttpServlet {

    // 실습용 하드코딩 계정 (실무에서는 DB 조회)
    private static final String VALID_USER = "admin";
    private static final String VALID_PASS = "1234";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if (path.endsWith("login")) {
            handleLogin(req, resp);
        } else if (path.endsWith("logout")) {
            handleLogout(req, resp);
        } else if (path.endsWith("notice")) {
            handleNotice(req, resp);
        }
    }

    /**
     * 로그인 처리
     * 1. 아이디/비밀번호 검증
     * 2. 성공 → 세션에 사용자 정보 저장 후 대시보드로 리다이렉트
     * 3. 실패 → 로그인 페이지로 리다이렉트 (에러 파라미터 포함)
     *
     * 핵심 포인트:
     * - getSession(true)  : 세션 없으면 새로 생성
     * - getSession(false) : 세션 없으면 null 반환 (새로 만들지 않음) ← 권장
     * - 로그인 성공 시 기존 세션을 invalidate 후 새 세션 발급 (세션 고정 공격 방어)
     */
    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (VALID_USER.equals(username) && VALID_PASS.equals(password)) {
            // 기존 세션이 있으면 무효화 (세션 고정 공격 방어)
            HttpSession oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            // 새 세션 생성 후 사용자 정보 저장
            HttpSession session = req.getSession(true);
            session.setAttribute("loginUser", username);
            session.setAttribute("loginTime",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            session.setMaxInactiveInterval(30 * 60); // 30분 타임아웃

            resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_dashboard.jsp");

        } else {
            // 로그인 실패
            resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_login.jsp?error=1");
        }
    }

    /**
     * 로그아웃 처리
     * session.invalidate() 로 서버의 세션 데이터 전부 삭제
     */
    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // 서버 측 세션 소멸
        }
        resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_login.jsp");
    }

    /**
     * "1주일간 공지 안보기" 쿠키 처리
     *
     * action=hide  → 7일짜리 쿠키 설정
     * action=close → 오늘만 (세션 쿠키, maxAge 설정 안함)
     * action=reset → 쿠키 삭제 (maxAge=0)
     *
     * 쿠키 핵심:
     * - maxAge > 0  : 지정 시간(초) 동안 브라우저에 저장
     * - maxAge = 0  : 쿠키 즉시 삭제
     * - maxAge = -1 : 브라우저 닫으면 삭제 (세션 쿠키, 기본값)
     */
    private void handleNotice(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        if ("hide".equals(action)) {
            // 7일간 안보기
            Cookie cookie = new Cookie("hideNotice", "true");
            cookie.setPath(req.getContextPath() + "/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 (초 단위)
            cookie.setHttpOnly(true); // JS에서 접근 불가 (보안)
            resp.addCookie(cookie);

        } else if ("close".equals(action)) {
            // 오늘만 닫기: 쿠키를 설정하지 않고 그냥 리다이렉트
            // 브라우저가 닫히거나 새 요청을 보내면 다시 팝업이 뜬다

        } else if ("reset".equals(action)) {
            // 쿠키 삭제: 같은 이름으로 maxAge=0 설정하면 삭제됨
            Cookie cookie = new Cookie("hideNotice", "");
            cookie.setPath(req.getContextPath() + "/");
            cookie.setMaxAge(0); // 즉시 삭제
            resp.addCookie(cookie);
        }

        resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_notice_popup.jsp");
    }
}

