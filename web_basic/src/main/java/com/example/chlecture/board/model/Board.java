package com.example.chlecture.board.model;

import java.util.Date;

public class Board {
    private int id;
    private String title;
    private String content;
    private String writer;
    private Date createdAt;
    // [Ch12] DB에는 "A"/"I"/"D" 문자열로 저장. Java에서는 BoardStatus Enum으로 변환하여 사용
    private String statusCode;

    public Board() {}

    public Board(int id, String title, String content, String writer, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.statusCode = "A"; // 기본값: 활성
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public String getStatusCode() { return statusCode; }
    public void setStatusCode(String statusCode) { this.statusCode = statusCode; }
}

