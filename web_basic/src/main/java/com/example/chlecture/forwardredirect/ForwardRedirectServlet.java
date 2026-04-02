package com.example.chlecture.forwardredirect;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/04_forward_redirect/handle")
public class ForwardRedirectServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String mode = req.getParameter("mode");
        req.setAttribute("msg","서버에서 설정한 메시지");
        if("redirect".equalsIgnoreCase(mode)) {
            resp.sendRedirect(req.getContextPath() + "/04_forward_redirect/04_result_redirect.jsp");
        } else {
            req.getRequestDispatcher("/04_forward_redirect/04_result_forward.jsp").forward(req, resp);
        }
    }
}
