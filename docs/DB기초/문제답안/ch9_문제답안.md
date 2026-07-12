---
layout: default
title: ch9_문제답안
---

# ch9 문제답안

[← ch9 문서로](../ch9_제약조건과테이블변경.md)

## 1. BOARD 테이블 생성

```sql
CREATE TABLE BOARD (
    BOARD_ID   NUMBER(10)
        CONSTRAINT PK_BOARD PRIMARY KEY,
    WRITER_ID  NUMBER(4)
        CONSTRAINT FK_BOARD_EMP REFERENCES EMP(EMP_ID)
        CONSTRAINT NN_BOARD_WRITER NOT NULL,
    TITLE      VARCHAR2(200) NOT NULL,
    CATEGORY   VARCHAR2(10)
        CONSTRAINT CK_BOARD_CATEGORY CHECK (CATEGORY IN ('공지', '자유', '질문')),
    REG_DATE   DATE DEFAULT SYSDATE
);
```

FK 를 테이블 레벨로 쓰는 것도 정답:

```sql
CONSTRAINT FK_BOARD_EMP FOREIGN KEY (WRITER_ID) REFERENCES EMP(EMP_ID)
```

## 2. 컬럼 추가

```sql
ALTER TABLE BOARD ADD VIEW_CNT NUMBER(10) DEFAULT 0;
```

## 3. 없는 사원으로 INSERT

```sql
INSERT INTO BOARD (BOARD_ID, WRITER_ID, TITLE, CATEGORY)
VALUES (1, 9999, '아무말', '자유');
-- ORA-02291: integrity constraint (FK_BOARD_EMP) violated - parent key not found
```

**외래키(FK_BOARD_EMP) 위반으로 실패한다.** 9999번 사원이 EMP(부모 테이블)에 없기 때문이다.  
CATEGORY '자유' 는 CHECK 를 통과하고, TITLE 도 있으므로 다른 제약은 문제없다.

## 4. EMP 제약조건 조회

```sql
SELECT CONSTRAINT_NAME, CONSTRAINT_TYPE
FROM   USER_CONSTRAINTS
WHERE  TABLE_NAME = 'EMP';
```

결과 예시:

| CONSTRAINT_NAME | CONSTRAINT_TYPE | 의미 |
|-----------------|-----------------|------|
| PK_EMP | P | 기본키 |
| FK_EMP_DEPT | R | 외래키 |
| SYS_C00xxxxx | C | EMP_NAME 의 NOT NULL (이름 없이 만들어서 SYS_ 자동 이름) |

테이블명은 반드시 대문자 `'EMP'` 로 조회해야 한다. (오라클이 이름을 대문자로 저장하므로)

## 5. UNIQUE 와 NULL

UNIQUE 는 "**값이 있다면** 중복 금지"라는 규칙이다.  
NULL 은 '값 없음'이어서 비교 자체가 성립하지 않고(NULL = NULL 도 참이 아니다), 오라클은 단일 컬럼 UNIQUE 에서 NULL 을 개수 제한 없이 허용한다.  
"이메일은 중복도 안 되고 반드시 있어야 한다"가 요구사항이라면 UNIQUE 와 **NOT NULL 을 함께** 걸어야 한다.
