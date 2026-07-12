---
layout: default
title: ch2_문제답안
---

# ch2 문제답안

[← ch2 문서로](../ch2_테이블생성과자료형.md)

## 1. MEMBER 테이블 생성

```sql
CREATE TABLE MEMBER (
    MEMBER_ID    NUMBER(10)         PRIMARY KEY,
    LOGIN_ID     VARCHAR2(20)       NOT NULL,
    MEMBER_NAME  VARCHAR2(10 CHAR),          -- 글자 수 단위 선언. VARCHAR2(30) 도 정답
    POINT        NUMBER(8)          DEFAULT 1000,
    JOIN_DATE    DATE               DEFAULT SYSDATE
);
```

- `MEMBER_NAME` 은 한글 10글자가 목표이므로 `VARCHAR2(10 CHAR)`(글자 수 단위) 또는 UTF-8 기준 바이트로 넉넉하게 `VARCHAR2(30)` 이면 된다. `VARCHAR2(10)` 은 한글 3글자밖에 못 넣으므로 오답.

확인:

```sql
DESC MEMBER;
INSERT INTO MEMBER (MEMBER_ID, LOGIN_ID, MEMBER_NAME) VALUES (1, 'hong123', '홍길동');
SELECT * FROM MEMBER;   -- POINT 1000, JOIN_DATE 현재시각이 자동으로 들어갔는지 확인
```

## 2. VARCHAR2(10) 에 한글 6글자

**오류가 나며 INSERT 가 실패한다.**

```
ORA-12899: value too large for column ... (actual: 18, maximum: 10)
```

UTF-8 환경에서 한글 1글자는 3바이트다. '데이터베이스' 6글자 = 18바이트 > 10바이트.  
`VARCHAR2(n)` 의 n 은 **글자 수가 아니라 바이트 수**이기 때문이다.

## 3. DROP vs TRUNCATE

- **공통점**: 데이터가 전부 삭제되고, 둘 다 DDL 이라 ROLLBACK 으로 되돌릴 수 없다.
- **차이점**: DROP 은 테이블 구조까지 삭제하고, TRUNCATE 는 구조(빈 테이블)는 남긴다.

## 4. 테이블 삭제

```sql
DROP TABLE MEMBER;
```
