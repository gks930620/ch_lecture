package com.example.chlecture.lifecycle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 서블릿 생명주기(Lifecycle) 실습용 서블릿
 *
 * 서블릿 생명주기:
 * 1. 클래스 로딩 + 인스턴스 생성 (최초 요청 시 1번만)
 * 2. init(ServletConfig) 호출 (1번만)
 * 3. service() → doGet/doPost 호출 (매 요청마다)
 * 4. destroy() 호출 (서버 종료 시 1번만)
 *
 * 핵심: 서블릿 인스턴스는 싱글톤! 멀티스레드로 동시 요청 처리
 *       → 인스턴스 변수(필드)에 사용자별 데이터를 저장하면 안 됨!
 */
@WebServlet("/lifecycle")
public class LifecycleServlet extends HttpServlet {

    private int requestCount = 0; // 주의: 모든 사용자가 공유하는 변수!

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("========== [LifecycleServlet] init() 호출됨 ==========");
        System.out.println("  → 서블릿 인스턴스가 생성되고 초기화됩니다. (최초 1회)");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestCount++; // 동시성 문제 발생 가능! (실습 포인트)
        System.out.println("[LifecycleServlet] doGet() 호출 - 요청 #" + requestCount
                + " | Thread: " + Thread.currentThread().getName());

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h1>서블릿 생명주기(Lifecycle) 실습</h1>");

        out.println("<h2>1) 이 서블릿의 상태</h2>");
        out.println("<p>요청 횟수(공유 변수): " + requestCount + "</p>");
        out.println("<p>현재 스레드: " + Thread.currentThread().getName() + "</p>");
        out.println("<p>서블릿 인스턴스 해시코드: " + this.hashCode() + "</p>");
        out.println("<p>▶ 새로고침하면 요청 횟수가 증가하지만 해시코드(인스턴스)는 동일합니다 = 싱글톤</p>");

        out.println("<h2>2) HTTP 요청 정보</h2>");
        out.println("<table border='1'>");
        out.println("<tr><td>Method</td><td>" + req.getMethod() + "</td></tr>");
        out.println("<tr><td>Request URI</td><td>" + req.getRequestURI() + "</td></tr>");
        out.println("<tr><td>Request URL</td><td>" + req.getRequestURL() + "</td></tr>");
        out.println("<tr><td>Query String</td><td>" + req.getQueryString() + "</td></tr>");
        out.println("<tr><td>Protocol</td><td>" + req.getProtocol() + "</td></tr>");
        out.println("<tr><td>Content-Type</td><td>" + req.getContentType() + "</td></tr>");
        out.println("<tr><td>Content-Length</td><td>" + req.getContentLength() + "</td></tr>");
        out.println("<tr><td>Remote Addr</td><td>" + req.getRemoteAddr() + "</td></tr>");
        out.println("<tr><td>Context Path</td><td>" + req.getContextPath() + "</td></tr>");
        out.println("<tr><td>Servlet Path</td><td>" + req.getServletPath() + "</td></tr>");
        out.println("</table>");

        out.println("<h2>3) HTTP 요청 헤더</h2>");
        out.println("<table border='1'>");
        java.util.Enumeration<String> headers = req.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            out.println("<tr><td>" + name + "</td><td>" + req.getHeader(name) + "</td></tr>");
        }
        out.println("</table>");

        out.println("<h2>4) 파라미터 테스트</h2>");
        out.println("<p>현재 name 파라미터: " + req.getParameter("name") + "</p>");
        out.println("<form action='" + req.getContextPath() + "/lifecycle' method='get'>");
        out.println("<input name='name' placeholder='이름 입력'/>");
        out.println("<button type='submit'>GET 전송</button>");
        out.println("</form>");
        out.println("<form action='" + req.getContextPath() + "/lifecycle' method='post'>");
        out.println("<input name='name' placeholder='이름 입력'/>");
        out.println("<button type='submit'>POST 전송</button>");
        out.println("</form>");

        out.println("<hr/>");
        out.println("<p><a href='" + req.getContextPath() + "/'>← 홈으로</a></p>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("[LifecycleServlet] doPost() 호출 - name=" + req.getParameter("name"));

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<h1>POST 요청 결과</h1>");
        out.println("<p>전달받은 name: " + req.getParameter("name") + "</p>");
        out.println("<p>요청 방식: " + req.getMethod() + "</p>");
        out.println("<p>▶ GET과 POST의 차이: GET은 쿼리스트링으로, POST는 요청 본문(body)으로 데이터 전달</p>");
        out.println("<hr/>");
        out.println("<p><a href='" + req.getContextPath() + "/lifecycle'>GET으로 돌아가기</a></p>");
        out.println("<p><a href='" + req.getContextPath() + "/'>← 홈으로</a></p>");
    }

    @Override
    public void destroy() {
        System.out.println("========== [LifecycleServlet] destroy() 호출됨 ==========");
        System.out.println("  → 서블릿이 제거됩니다. (서버 종료 시)");
    }
}

