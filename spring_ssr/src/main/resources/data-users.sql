-- Hibernate DDL 이후 실행됨 (defer-datasource-initialization: true)
-- users 테이블만 FK 체크 없이 강제 재생성 (이전 스키마 잔재 제거용)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255),
    nickname   VARCHAR(255),
    is_deleted BIT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY UK_users_username (username)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;

-- users 더미 데이터 (password: test1234 평문)
INSERT INTO users (id, username, password, email, nickname) VALUES
(1,  'user1',  'test1234', 'user1@example.com',  '홍길동'),
(2,  'user2',  'test1234', 'user2@example.com',  '김영희'),
(3,  'user3',  'test1234', 'user3@example.com',  '이철수'),
(4,  'user4',  'test1234', 'user4@example.com',  '김민수'),
(5,  'user5',  'test1234', 'user5@example.com',  '이지은'),
(6,  'user6',  'test1234', 'user6@example.com',  '박서준'),
(7,  'user7',  'test1234', 'user7@example.com',  '정수아'),
(8,  'user8',  'test1234', 'user8@example.com',  '최현우'),
(9,  'user9',  'test1234', 'user9@example.com',  '강예진'),
(10, 'user10', 'test1234', 'user10@example.com', '윤서현'),
(11, 'user11', 'test1234', 'user11@example.com', '임도윤'),
(12, 'user12', 'test1234', 'user12@example.com', '오지아'),
(13, 'user13', 'test1234', 'user13@example.com', '장서준'),
(14, 'user14', 'test1234', 'user14@example.com', '조유나'),
(15, 'user15', 'test1234', 'user15@example.com', '하준혁'),
(16, 'user16', 'test1234', 'user16@example.com', '신채원'),
(17, 'user17', 'test1234', 'user17@example.com', '안시우'),
(18, 'user18', 'test1234', 'user18@example.com', '홍지우'),
(19, 'user19', 'test1234', 'user19@example.com', '송민준'),
(20, 'user20', 'test1234', 'user20@example.com', '고하은');

