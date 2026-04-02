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
    roles      VARCHAR(255) NOT NULL DEFAULT 'USER',
    is_deleted BIT          NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY UK_users_username (username)
) ENGINE=InnoDB;

SET FOREIGN_KEY_CHECKS = 1;

-- users 더미 데이터
-- password: 평문(test1234)으로 INSERT → DataInitializer가 서버 시작 시 BCrypt로 자동 인코딩
-- role: USER(일반), ADMIN(관리자) — 쉼표 구분으로 복수 권한 가능
INSERT INTO users (id, username, password, email, nickname, roles) VALUES
(1,  'admin',  'test1234', 'admin@example.com',  '관리자',   'ADMIN,USER'),
(2,  'user1',  'test1234', 'user1@example.com',  '홍길동',   'USER'),
(3,  'user2',  'test1234', 'user2@example.com',  '김영희',   'USER'),
(4,  'user3',  'test1234', 'user3@example.com',  '이철수',   'USER'),
(5,  'user4',  'test1234', 'user4@example.com',  '김민수',   'USER'),
(6,  'user5',  'test1234', 'user5@example.com',  '이지은',   'USER'),
(7,  'user6',  'test1234', 'user6@example.com',  '박서준',   'USER'),
(8,  'user7',  'test1234', 'user7@example.com',  '정수아',   'USER'),
(9,  'user8',  'test1234', 'user8@example.com',  '최현우',   'USER'),
(10, 'user9',  'test1234', 'user9@example.com',  '강예진',   'USER'),
(11, 'user10', 'test1234', 'user10@example.com', '윤서현',   'USER'),
(12, 'user11', 'test1234', 'user11@example.com', '임도윤',   'USER'),
(13, 'user12', 'test1234', 'user12@example.com', '오지아',   'USER'),
(14, 'user13', 'test1234', 'user13@example.com', '장서준',   'USER'),
(15, 'user14', 'test1234', 'user14@example.com', '조유나',   'USER'),
(16, 'user15', 'test1234', 'user15@example.com', '하준혁',   'USER');
