---
layout: default
title: ch2_테이블생성과자료형
description: CREATE TABLE, 오라클 자료형(VARCHAR2/NUMBER/DATE), DEFAULT, DROP/TRUNCATE
---

# ch2_테이블생성과자료형

---

## 학습 목표
- CREATE TABLE 로 테이블을 만들 수 있다.
- 오라클의 주요 자료형(VARCHAR2, CHAR, NUMBER, DATE)을 구분해서 쓸 수 있다.
- DEFAULT, NOT NULL, PRIMARY KEY 를 붙일 수 있다.
- DROP 과 TRUNCATE 의 차이를 안다.

---

## 1. CREATE TABLE 기본 형태

```sql
CREATE TABLE 테이블명 (
    컬럼명1  자료형,
    컬럼명2  자료형,
    ...
);
```

가장 단순한 예:

```sql
CREATE TABLE MEMO (
    TITLE    VARCHAR2(100),
    CONTENT  VARCHAR2(2000)
);
```

만든 테이블의 구조 확인:

```sql
DESC MEMO;          -- DESCRIBE 의 줄임. 컬럼명/NULL허용/자료형이 보인다
```

### 1.1 이름 규칙

- 테이블명/컬럼명은 문자로 시작, 30바이트 이내(12.2 버전부터 128바이트), 영문·숫자·`_` 사용.
- 대소문자를 구분하지 않고 내부적으로 **대문자로 저장**된다. `memo` 로 만들어도 `MEMO`.
- `ORDER`, `SELECT` 같은 **예약어는 테이블명/컬럼명으로 못 쓴다**.  
  (실습테이블에서 주문 테이블 이름이 `ORDER` 가 아니라 `ORDERS` 인 이유)
- 한글 테이블명도 기술적으로는 가능하지만 실무에서는 쓰지 않는다. **이름은 영어, 데이터는 한글/영어 자유** — 이게 표준적인 모습이다.

---

## 2. 오라클 자료형

컬럼마다 "여기엔 어떤 종류의 값이 들어간다"를 정하는 것이 자료형이다. 실무에서 쓰는 것은 사실상 4~5개다.

| 자료형 | 저장하는 것 | 예 |
|--------|-------------|----|
| `VARCHAR2(n)` | 가변 길이 문자열 (최대 n 바이트) | 이름, 이메일, 제목 |
| `CHAR(n)` | 고정 길이 문자열 (항상 n 바이트) | 'Y'/'N' 플래그 정도 |
| `NUMBER(p, s)` | 숫자 (전체 p자리, 소수점 이하 s자리) | 금액, 수량, 비율 |
| `DATE` | 날짜 + 시각 (년월일시분초) | 입사일, 주문일 |
| `TIMESTAMP` | DATE + 소수점 초 | 로그 기록 시각 |
| `CLOB` | 아주 긴 문자열 (4GB) | 게시글 본문 |
| `BLOB` | 이진 데이터 | 이미지, 파일 |

### 2.1 VARCHAR2 vs CHAR

- `VARCHAR2(20)` 에 `'abc'` 를 넣으면 3바이트만 차지한다. → **거의 항상 VARCHAR2 를 쓴다.**
- `CHAR(20)` 에 `'abc'` 를 넣으면 뒤에 공백을 채워 항상 20바이트. 비교할 때도 공백 때문에 헷갈린다.

주의: `VARCHAR2(20)` 의 20은 **글자 수가 아니라 바이트 수**다.  
한글은 인코딩(UTF-8 기준)에 따라 한 글자가 3바이트라서 `VARCHAR2(20)` 에는 한글 6글자밖에 못 넣는다.  
한글이 들어갈 컬럼은 여유 있게 잡거나 `VARCHAR2(20 CHAR)` 처럼 글자 수 단위로 선언할 수 있다.

### 2.2 NUMBER

```sql
NUMBER          -- 자릿수 지정 안 함 (최대 38자리 유효숫자까지)
NUMBER(7)       -- 정수 최대 7자리 (9,999,999 까지)
NUMBER(7, 2)    -- 전체 7자리 중 소수점 이하 2자리 (99999.99 까지)
```

오라클에는 Java 의 int/long/double 구분이 없다. 전부 NUMBER 하나로 처리한다.

### 2.3 DATE

DATE 는 이름과 달리 **시각(시분초)까지** 저장한다. 날짜 다루는 법은 ch5 에서 자세히 배운다.

---

## 3. DEFAULT 와 기본 제약조건

```sql
CREATE TABLE BOARD (
    BOARD_ID   NUMBER(10)     PRIMARY KEY,            -- 기본키
    TITLE      VARCHAR2(200)  NOT NULL,               -- NULL 금지
    CONTENT    CLOB,
    VIEW_CNT   NUMBER(10)     DEFAULT 0,              -- 값 생략 시 0
    REG_DATE   DATE           DEFAULT SYSDATE         -- 값 생략 시 현재 시각
);
```

- `NOT NULL` — 이 컬럼은 비워둘 수 없다. (NULL = "값 없음". ch4 에서 자세히)
- `PRIMARY KEY` — 기본키. **중복 금지 + NULL 금지**가 자동으로 적용된다.
- `DEFAULT 값` — INSERT 할 때 값을 안 주면 대신 들어갈 값.
- `SYSDATE` — 현재 날짜/시각을 돌려주는 오라클 내장 함수.

제약조건은 종류가 더 많다(UNIQUE, CHECK, FOREIGN KEY). ch9 에서 제대로 다루고, 지금은 위 세 개면 충분하다.

---

## 4. 테이블 삭제 — DROP, TRUNCATE

```sql
DROP TABLE BOARD;       -- 테이블 구조 + 데이터 전부 삭제
TRUNCATE TABLE BOARD;   -- 구조는 남기고 데이터만 전부 삭제
```

| | DROP | TRUNCATE | (비교) DELETE |
|---|------|----------|---------------|
| 구조 | 삭제 | 유지 | 유지 |
| 데이터 | 삭제 | 전부 삭제 | 조건에 맞는 것만 삭제 가능 |
| ROLLBACK 으로 복구 | 불가 | 불가 | **가능** |

DROP/TRUNCATE 는 DDL 이라 실행 즉시 확정된다(되돌리기 불가). DELETE 는 DML 이라 ROLLBACK 할 수 있다 — 이 차이는 ch3, ch12 에서 다시 본다.

> 참고: 실수로 DROP 한 테이블은 `FLASHBACK TABLE 테이블명 TO BEFORE DROP;` 으로 살릴 수 있는 경우도 있다(휴지통 기능). 되면 다행인 것이지 믿고 쓸 건 아니다.

---

## 5. 실습테이블.sql 다시 보기

ch1 에서 실행한 [실습테이블.sql](실습테이블.sql) 을 열어 보면 이제 대부분 읽을 수 있다.

```sql
CREATE TABLE DEPT (
    DEPT_ID    NUMBER(4)     CONSTRAINT PK_DEPT PRIMARY KEY,
    DEPT_NAME  VARCHAR2(30)  NOT NULL,
    LOC        VARCHAR2(30)
);
```

- `CONSTRAINT PK_DEPT PRIMARY KEY` — 제약조건에 `PK_DEPT` 라는 이름을 붙인 것. 이름을 생략해도 되지만(오라클이 `SYS_C…` 라는 임의 이름을 붙임), 이름이 있어야 오류 메시지를 읽기 쉽다. ch9 에서 자세히.
- EMP 테이블의 `FOREIGN KEY ... REFERENCES DEPT(DEPT_ID)` — "EMP.DEPT_ID 에는 DEPT 에 실제로 있는 부서번호만 들어갈 수 있다"는 제약. 역시 ch9 에서.

---

## 정리

- `CREATE TABLE 이름 (컬럼 자료형, ...)` 으로 테이블을 만들고, `DESC` 로 구조를 확인한다.
- 문자열은 `VARCHAR2`, 숫자는 `NUMBER`, 날짜는 `DATE` — 이 셋이 90%다. VARCHAR2 의 크기는 바이트 단위라 한글은 여유 있게.
- `DEFAULT`, `NOT NULL`, `PRIMARY KEY` 로 컬럼에 규칙을 건다.
- DROP 은 테이블째 삭제, TRUNCATE 는 데이터만 전부 삭제. 둘 다 되돌릴 수 없다.

---

## 문제

1. 다음 요구사항대로 회원 테이블 `MEMBER` 를 만드는 SQL 을 작성하시오.
   - `MEMBER_ID` : 숫자 최대 10자리, 기본키
   - `LOGIN_ID` : 영문 아이디 최대 20바이트, NULL 금지
   - `MEMBER_NAME` : 한글 이름 (한글 10글자가 들어갈 수 있게)
   - `POINT` : 숫자 최대 8자리, 값을 안 주면 자동으로 1000
   - `JOIN_DATE` : 날짜, 값을 안 주면 자동으로 현재 시각
2. `VARCHAR2(10)` 컬럼에 `'데이터베이스'`(한글 6글자) 를 넣으면 어떻게 되는가? 이유와 함께 설명하시오. (UTF-8 환경 기준)
3. DROP TABLE 과 TRUNCATE TABLE 의 공통점 1가지와 차이점 1가지를 쓰시오.
4. 1번에서 만든 MEMBER 테이블을 삭제하는 SQL 을 작성하시오.

→ [답안 보기](문제답안/ch2_문제답안.md)
