-- CODE 테이블 예시
-- ※ 강의에서 "왜 이 방식보다 Enum이 나은가"를 설명하기 위한 참고용
CREATE TABLE IF NOT EXISTS code (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(50),
    code     VARCHAR(50),
    label    VARCHAR(100)
);

-- 게시글 상태 코드 (DB 코드 테이블 방식의 예시)
INSERT INTO code(category, code, label) VALUES ('board_status', 'A', '활성');
INSERT INTO code(category, code, label) VALUES ('board_status', 'I', '비활성');
INSERT INTO code(category, code, label) VALUES ('board_status', 'D', '삭제');

