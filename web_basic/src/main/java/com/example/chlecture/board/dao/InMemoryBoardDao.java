package com.example.chlecture.board.dao;

import com.example.chlecture.board.model.Board;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBoardDao implements BoardDao {
    private final Map<Integer, Board> store = Collections.synchronizedMap(new LinkedHashMap<>());
    private final AtomicInteger seq = new AtomicInteger(1);

    public InMemoryBoardDao() {
        // 샘플 데이터
        Board b = new Board(seq.getAndIncrement(), "첫 글", "내용입니다.", "teacher", new Date());
        store.put(b.getId(), b);
    }

    @Override
    public List<Board> selectList(Map<String, Object> params) {
        List<Board> list = new ArrayList<>(store.values());
        // 최신 글 먼저
        Collections.reverse(list);
        if (params != null && params.containsKey("offset") && params.containsKey("limit")) {
            int offset = (int) params.get("offset");
            int limit = (int) params.get("limit");
            int from = Math.max(0, offset);
            int to = Math.min(list.size(), offset + limit);
            return list.subList(from, to);
        }
        return list;
    }

    @Override
    public Board selectOne(int id) {
        return store.get(id);
    }

    @Override
    public int insert(Board board) {
        int id = seq.getAndIncrement();
        board.setId(id);
        if (board.getCreatedAt() == null) board.setCreatedAt(new Date());
        if (board.getStatusCode() == null) board.setStatusCode("A"); // 기본값: 활성
        store.put(id, board);
        return 1;
    }

    @Override
    public int update(Board board) {
        if (!store.containsKey(board.getId())) return 0;
        board.setCreatedAt(store.get(board.getId()).getCreatedAt());
        store.put(board.getId(), board);
        return 1;
    }

    @Override
    public int delete(int id) {
        return store.remove(id) != null ? 1 : 0;
    }
}
