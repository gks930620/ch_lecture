-- 게시판 테이블
-- [Ch12] status 컬럼 추가: VARCHAR로 "A"/"I"/"D" 저장, Java에서는 BoardStatus Enum으로 관리
CREATE TABLE IF NOT EXISTS board (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT,
    writer     VARCHAR(100),
    status     VARCHAR(10) DEFAULT 'A',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 샘플 데이터
INSERT INTO board(title, content, writer, status) VALUES('첫 글', '내용입니다', 'teacher', 'A');

