package com.example.chlecture.board.controller;

import com.example.chlecture.board.dao.BoardDao;
import com.example.chlecture.board.dao.InMemoryBoardDao;
import com.example.chlecture.board.model.Board;
import com.example.chlecture.code.BoardStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    private final BoardDao dao = new InMemoryBoardDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/".equals(path) || "/list".equals(path)) {
            List<Board> list = dao.selectList(null);
            // [Ch12] statusCode → Enum label 변환 Map
            Map<Integer, String> statusLabels = new HashMap<>();
            for (Board b : list) {
                if (b.getStatusCode() != null) {
                    statusLabels.put(b.getId(), BoardStatus.fromCode(b.getStatusCode()).getLabel());
                }
            }
            req.setAttribute("list", list);
            req.setAttribute("statusLabels", statusLabels);
            req.setAttribute("statusValues", BoardStatus.values());
            req.getRequestDispatcher("/10_mybatis_board/board_list.jsp").forward(req, resp);
            return;
        }
        if ("/view".equals(path)) {
            String idStr = req.getParameter("id");
            int id = 0;
            try { id = Integer.parseInt(idStr); } catch (Exception ignored) {}
            Board b = dao.selectOne(id);
            if (b != null && b.getStatusCode() != null) {
                BoardStatus status = BoardStatus.fromCode(b.getStatusCode());
                req.setAttribute("statusLabel", status.getLabel());
            }
            req.setAttribute("board", b);
            req.getRequestDispatcher("/10_mybatis_board/board_view.jsp").forward(req, resp);
            return;
        }
        if ("/write".equals(path)) {
            // [Ch12] 글쓰기 폼에 상태 선택 옵션 전달
            req.setAttribute("statusValues", BoardStatus.values());
            req.getRequestDispatcher("/10_mybatis_board/board_write.jsp").forward(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/create".equals(path)) {
            req.setCharacterEncoding("UTF-8");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            String writer = req.getParameter("writer");
            String statusParam = req.getParameter("status");

            // [Ch12] Enum으로 검증 — 잘못된 코드값이면 fromCode()에서 예외 발생
            BoardStatus status = BoardStatus.fromCode(statusParam != null ? statusParam : "A");

            Board b = new Board();
            b.setTitle(title);
            b.setContent(content);
            b.setWriter(writer);
            b.setStatusCode(status.getCode()); // Enum → String 코드로 저장
            dao.insert(b);
            resp.sendRedirect(req.getContextPath() + "/board/list");
            return;
        }
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}

