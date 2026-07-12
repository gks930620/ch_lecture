---
layout: default
title: ch9_제약조건과테이블변경
description: PK/FK/UNIQUE/CHECK/NOT NULL 제약조건, ALTER TABLE, 데이터 딕셔너리
---

# ch9_제약조건과테이블변경

---

## 학습 목표
- 5가지 제약조건(NOT NULL, UNIQUE, PRIMARY KEY, FOREIGN KEY, CHECK)을 설명하고 걸 수 있다.
- 외래키(FK)가 부모-자식 테이블을 어떻게 보호하는지 안다.
- ALTER TABLE 로 만들어진 테이블을 변경할 수 있다.
- USER_CONSTRAINTS 로 제약조건을 조회할 수 있다.

제약조건(constraint)은 **잘못된 데이터가 들어오는 것을 DB 차원에서 막는 규칙**이다.  
"애플리케이션에서 검사하면 되지 않나?" — 프로그램은 여러 개(웹, 배치, 관리자툴…)지만 DB 는 하나다. 마지막 방어선은 DB 에 있어야 한다.

---

## 1. 제약조건 5가지

| 제약조건 | 규칙 | 예 |
|----------|------|----|
| `NOT NULL` | NULL 금지 | 이름은 반드시 있어야 |
| `UNIQUE` | 중복 금지 (NULL 은 허용) | 이메일 중복 불가 |
| `PRIMARY KEY` | 중복 금지 + NULL 금지, 테이블당 1개 | 사원번호 |
| `FOREIGN KEY` | 다른 테이블(부모)에 있는 값만 허용 | 부서번호는 DEPT 에 있는 것만 |
| `CHECK` | 지정한 조건을 만족하는 값만 | 월급은 0 이상 |

한 번에 다 걸어 보면:

```sql
CREATE TABLE MEMBER (
    MEMBER_ID   NUMBER(10)
        CONSTRAINT PK_MEMBER PRIMARY KEY,
    LOGIN_ID    VARCHAR2(20)
        CONSTRAINT UQ_MEMBER_LOGIN UNIQUE
        CONSTRAINT NN_MEMBER_LOGIN NOT NULL,
    MEMBER_NAME VARCHAR2(30)  NOT NULL,                -- 이름 없는 제약도 가능
    GENDER      CHAR(1)
        CONSTRAINT CK_MEMBER_GENDER CHECK (GENDER IN ('M', 'F')),
    POINT       NUMBER(8) DEFAULT 0
        CONSTRAINT CK_MEMBER_POINT CHECK (POINT >= 0),
    DEPT_ID     NUMBER(4)
        CONSTRAINT FK_MEMBER_DEPT REFERENCES DEPT(DEPT_ID)
);
```

- `CONSTRAINT 이름 규칙` 형태. 이름을 생략하면 오라클이 `SYS_C0012345` 같은 이름을 자동으로 붙인다.
- **이름을 붙이는 이유**: 오류 메시지에 제약조건 이름이 나온다. `PK_MEMBER violated` 는 바로 알아보지만 `SYS_C0012345 violated` 는 찾아봐야 한다.
- 관례: `PK_테이블`, `FK_테이블_참조`, `UQ_`, `CK_`, `NN_`.

### 1.1 테이블 레벨 정의

컬럼 뒤에 쓰는 대신, 컬럼 정의를 다 끝내고 모아서 쓸 수도 있다. **두 컬럼 이상을 묶는 제약은 이 방식만 가능**하다.

```sql
CREATE TABLE ORDER_ITEM (
    ORDER_ID    NUMBER(6),
    LINE_NO     NUMBER(3),
    PRODUCT_ID  NUMBER(4) NOT NULL,
    QUANTITY    NUMBER(4) NOT NULL,
    CONSTRAINT PK_ORDER_ITEM PRIMARY KEY (ORDER_ID, LINE_NO),   -- 복합 기본키
    CONSTRAINT FK_ORDER_ITEM_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);
```

주문번호+행번호 조합이 기본키인 예다. (1번 주문의 1행, 1번 주문의 2행…)

---

## 2. 외래키(FK) 자세히 — 부모와 자식

실습테이블의 관계:

```
DEPT (부모) 1 ──── N EMP (자식)          : EMP.DEPT_ID → DEPT.DEPT_ID
PRODUCT (부모) 1 ── N ORDERS (자식)      : ORDERS.PRODUCT_ID → PRODUCT.PRODUCT_ID
EMP (부모) 1 ────── N ORDERS (자식)      : ORDERS.EMP_ID → EMP.EMP_ID
```

FK 가 막아주는 것 두 가지:

```sql
-- 1) 부모(DEPT)에 없는 값을 자식(EMP)에 입력 금지
INSERT INTO EMP (EMP_ID, EMP_NAME, DEPT_ID) VALUES (1099, '유령', 99);
-- ORA-02291: parent key not found  (99번 부서는 없다)

-- 2) 자식이 참조 중인 부모 삭제 금지
DELETE FROM DEPT WHERE DEPT_ID = 20;
-- ORA-02292: child record found  (개발팀 사원들이 있다)
```

### 2.1 ON DELETE 옵션

부모를 지울 때 자식을 어떻게 할지 정할 수 있다.

```sql
-- 부모가 지워지면 자식도 같이 삭제
FOREIGN KEY (DEPT_ID) REFERENCES DEPT(DEPT_ID) ON DELETE CASCADE

-- 부모가 지워지면 자식의 FK 컬럼을 NULL 로
FOREIGN KEY (DEPT_ID) REFERENCES DEPT(DEPT_ID) ON DELETE SET NULL
```

기본값(아무것도 안 씀)은 "자식 있으면 삭제 거부"다. CASCADE 는 편하지만 연쇄 삭제 사고 위험이 있어서 신중히 쓴다.

---

## 3. ALTER TABLE — 만든 테이블 고치기

운영 중인 테이블은 DROP 하고 다시 만들 수 없다. ALTER 로 고친다.

```sql
-- 컬럼 추가
ALTER TABLE MEMBER ADD PHONE VARCHAR2(20);

-- 컬럼 자료형/크기 변경 (데이터가 이미 있으면 줄이기는 제한됨)
ALTER TABLE MEMBER MODIFY PHONE VARCHAR2(30);

-- 컬럼 이름 변경
ALTER TABLE MEMBER RENAME COLUMN PHONE TO MOBILE;

-- 컬럼 삭제
ALTER TABLE MEMBER DROP COLUMN MOBILE;

-- 제약조건 추가
ALTER TABLE MEMBER ADD CONSTRAINT UQ_MEMBER_NAME UNIQUE (MEMBER_NAME);

-- NOT NULL 은 MODIFY 로
ALTER TABLE MEMBER MODIFY GENDER NOT NULL;

-- 제약조건 삭제
ALTER TABLE MEMBER DROP CONSTRAINT UQ_MEMBER_NAME;

-- 테이블 이름 변경
RENAME MEMBER TO MEMBERS;
```

이미 들어있는 데이터가 새 제약을 위반하면 제약 추가가 실패한다.  
(예: 중복 이메일이 이미 있는데 UNIQUE 를 걸면 → 데이터 정리부터 해야 한다)

---

## 4. 데이터 딕셔너리 — 내 테이블/제약조건 확인

오라클은 테이블/제약조건 정보 자체를 시스템 테이블(데이터 딕셔너리)에 저장한다. `USER_` 로 시작하는 뷰가 "내 계정 소유" 목록이다.

```sql
-- 내 테이블 목록
SELECT TABLE_NAME FROM USER_TABLES;

-- 특정 테이블의 제약조건 목록
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE, SEARCH_CONDITION
FROM   USER_CONSTRAINTS
WHERE  TABLE_NAME = 'EMP';    -- 테이블명은 대문자로!

-- 제약조건이 어느 컬럼에 걸렸는지
SELECT CONSTRAINT_NAME, COLUMN_NAME
FROM   USER_CONS_COLUMNS
WHERE  TABLE_NAME = 'EMP';
```

CONSTRAINT_TYPE: `P`=기본키, `R`=외래키(Referential), `U`=UNIQUE, `C`=CHECK/NOT NULL.

---

## 정리

- 제약조건은 DB 차원의 데이터 검증: NOT NULL / UNIQUE / PK(유일+필수) / FK(부모에 있는 값만) / CHECK(조건).
- 제약조건에는 이름을 붙이자(`PK_테이블` 등) — 오류 메시지가 읽기 쉬워진다.
- FK 는 "없는 부모값 입력"과 "참조되는 부모 삭제"를 막는다. ON DELETE CASCADE/SET NULL 로 동작 변경 가능.
- 운영 테이블 변경은 `ALTER TABLE ADD / MODIFY / RENAME COLUMN / DROP COLUMN / ADD CONSTRAINT`.
- 내 테이블/제약 확인: `USER_TABLES`, `USER_CONSTRAINTS`.

---

## 문제

1. 다음 요구사항으로 게시판 테이블 `BOARD` 를 만드시오. (제약조건 이름 포함)
   - `BOARD_ID` 숫자 10자리, 기본키 (PK_BOARD)
   - `WRITER_ID` 숫자 4자리, EMP 테이블의 EMP_ID 를 참조하는 외래키 (FK_BOARD_EMP), NULL 금지
   - `TITLE` 200바이트 문자열, NULL 금지
   - `CATEGORY` 문자열, '공지'/'자유'/'질문' 만 허용 (CK_BOARD_CATEGORY)
   - `REG_DATE` 날짜, 기본값 현재시각
2. 1번 BOARD 테이블에 조회수 `VIEW_CNT`(숫자 10자리, 기본값 0) 컬럼을 추가하는 ALTER 문을 작성하시오.
3. BOARD 에 `WRITER_ID = 9999, TITLE = '아무말', CATEGORY = '자유'` 인 글을 INSERT 하면 어떻게 되는가? (BOARD_ID 는 1로) 오류가 난다면 어떤 제약조건 때문인가?
4. EMP 테이블에 걸려 있는 제약조건의 이름과 종류를 조회하는 SQL 을 작성하고 실행 결과를 확인하시오.
5. (생각해보기) 회원 테이블의 이메일 컬럼에 UNIQUE 를 걸었다. 이메일이 NULL 인 회원이 2명 있어도 위반이 아닌 이유는?

→ [답안 보기](문제답안/ch9_문제답안.md)
