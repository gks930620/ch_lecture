package com.example.chlecture.board.dao;

import java.util.List;
import com.example.chlecture.board.model.Board;

public interface BoardDao {
    List<Board> selectList(java.util.Map<String,Object> params);
    Board selectOne(int id);
    int insert(Board board);
    int update(Board board);
    int delete(int id);
}
