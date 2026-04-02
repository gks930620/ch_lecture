package com.example.chlecture.cookiesession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/05_cookie_session/cookie", "/05_cookie_session/session"})
public class CookieSessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if(path.endsWith("cookie")) {
            String name = req.getParameter("cookieName");
            String action = req.getParameter("action");

            if ("delete".equals(action)) {
                // 삭제: maxAge=0 으로 덮어쓰면 브라우저가 해당 쿠키를 삭제함
                Cookie c = new Cookie(name, "");
                c.setPath(req.getContextPath());
                c.setMaxAge(0);
                resp.addCookie(c);
            } else {
                // 생성
                String value = req.getParameter("cookieValue");
                String maxAgeParam = req.getParameter("maxAge");
                Cookie c = new Cookie(name, value);
                c.setPath(req.getContextPath());
                if (maxAgeParam != null && !maxAgeParam.trim().isEmpty()) {
                    c.setMaxAge(Integer.parseInt(maxAgeParam));
                }
                resp.addCookie(c);
            }
            resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_cookie_demo.jsp");
        } else if(path.endsWith("session")) {
            String sval = req.getParameter("sval");
            HttpSession session = req.getSession();
            session.setAttribute("sval", sval);
            resp.sendRedirect(req.getContextPath() + "/05_cookie_session/05_session_demo.jsp");
        }
    }
}
