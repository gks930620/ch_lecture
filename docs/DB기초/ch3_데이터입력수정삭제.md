---
layout: default
title: ch3_데이터입력수정삭제
description: INSERT, UPDATE, DELETE 와 COMMIT/ROLLBACK 기초
---

# ch3_데이터입력수정삭제

---

## 학습 목표
- INSERT 로 데이터를 넣고, UPDATE 로 고치고, DELETE 로 지울 수 있다.
- WHERE 없는 UPDATE/DELETE 가 왜 위험한지 안다.
- COMMIT / ROLLBACK 이 무엇인지 알고, 변경 후 반드시 COMMIT 하는 습관을 들인다.

> 이 챕터 실습은 실습테이블을 마음껏 망가뜨려도 된다.  
> 언제든 [실습테이블.sql](실습테이블.sql) 을 다시 실행하면 처음 상태로 돌아온다.

---

## 1. INSERT — 데이터 넣기

### 1.1 기본 형태

```sql
-- 모든 컬럼에 값을 넣을 때 (테이블 만든 순서대로)
INSERT INTO DEPT VALUES (50, '총무팀', '서울');

-- 컬럼을 지정해서 넣을 때 (지정 안 한 컬럼은 NULL 또는 DEFAULT)
INSERT INTO DEPT (DEPT_ID, DEPT_NAME) VALUES (60, '재무팀');
```

두 번째 방식(컬럼 지정)을 권장한다. 이유:

- 나중에 테이블에 컬럼이 추가되어도 SQL 이 안 깨진다.
- 어떤 컬럼에 뭘 넣는지 SQL 만 봐도 안다.

### 1.2 자료형별 값 쓰는 법

```sql
INSERT INTO EMP (EMP_ID, EMP_NAME, JOB, HIRE_DATE, SALARY, EMAIL, DEPT_ID)
VALUES (1013, '박보검', '사원', TO_DATE('2025-01-06','YYYY-MM-DD'), 3300, 'park.bg@daehan.com', 20);
```

- 숫자: 그냥 쓴다 → `3300`
- 문자: 작은따옴표 → `'박보검'` (큰따옴표 아님!)
- 날짜: `TO_DATE('문자열', '형식')` 으로 변환해서 넣는 것이 안전하다. (ch5 에서 상세히)
- NULL: `NULL` 이라고 쓰거나, 컬럼 지정 방식에서 그 컬럼을 빼면 된다.

### 1.3 자주 만나는 오류

```sql
INSERT INTO DEPT VALUES (10, '중복팀', '서울');
-- ORA-00001: unique constraint (PK_DEPT) violated  → 기본키 10이 이미 있음

INSERT INTO DEPT (DEPT_ID, LOC) VALUES (70, '서울');
-- ORA-01400: cannot insert NULL into ("...DEPT_NAME")  → NOT NULL 컬럼을 비움

INSERT INTO EMP (EMP_ID, EMP_NAME, DEPT_ID) VALUES (1099, '유령', 99);
-- ORA-02291: integrity constraint (FK_EMP_DEPT) violated  → 99번 부서가 DEPT 에 없음
```

오류 메시지에 아까 붙인 **제약조건 이름(PK_DEPT 등)이 나온다.** 이름을 잘 지어두면 어디가 문제인지 바로 안다.

---

## 2. UPDATE — 데이터 고치기

```sql
UPDATE 테이블명
SET    컬럼 = 새값 [, 컬럼2 = 새값2, ...]
WHERE  조건;
```

```sql
-- 1004번 사원의 월급을 4300으로 변경
UPDATE EMP
SET    SALARY = 4300
WHERE  EMP_ID = 1004;

-- 1005번 사원의 직급과 월급을 동시에 변경
UPDATE EMP
SET    JOB = '대리', SALARY = 3600
WHERE  EMP_ID = 1005;

-- 기존 값을 이용한 변경: 개발팀(20) 전원 월급 10% 인상
UPDATE EMP
SET    SALARY = SALARY * 1.1
WHERE  DEPT_ID = 20;
```

### 2.1 WHERE 를 빼먹으면

```sql
UPDATE EMP SET SALARY = 0;   -- 사원 12명 전원의 월급이 0이 된다!!
```

**WHERE 가 없으면 전체 행이 대상이다.** 오류도 경고도 없이 그냥 실행된다.  
실무 사고 1순위. UPDATE/DELETE 를 쓸 때는 WHERE 부터 쓰는 습관을 들이자.

> 안전한 순서: 같은 WHERE 로 `SELECT` 를 먼저 해서 몇 건이 대상인지 확인 → 그 다음 UPDATE/DELETE.

---

## 3. DELETE — 데이터 지우기

```sql
DELETE FROM 테이블명 WHERE 조건;
```

```sql
-- 1013번 사원 삭제
DELETE FROM EMP WHERE EMP_ID = 1013;

-- 60번 부서 삭제
DELETE FROM DEPT WHERE DEPT_ID = 60;
```

역시 **WHERE 를 빼면 전체 삭제**다.

```sql
DELETE FROM EMP;   -- 사원 전원 삭제 (ROLLBACK 은 가능)
```

참고: 사원이 있는 부서(예: 20)를 지우려 하면 오류가 난다.

```sql
DELETE FROM DEPT WHERE DEPT_ID = 20;
-- ORA-02292: child record found  → EMP 가 20번 부서를 참조 중이라 못 지움
```

부모(DEPT)를 지우려면 자식(EMP)부터 정리해야 한다. 외래키(FK)의 보호 기능이다. (ch9)

---

## 4. COMMIT / ROLLBACK — 확정과 취소

INSERT/UPDATE/DELETE 를 실행해도 **아직 진짜 저장된 것이 아니다.**  
내 세션(접속)에서만 바뀐 것처럼 보이는 **임시 상태**다.

```sql
COMMIT;     -- 지금까지의 변경을 진짜로 확정. 다른 사람에게도 보인다
ROLLBACK;   -- 마지막 COMMIT 이후의 변경을 전부 취소
```

직접 실험해 보자.

```sql
DELETE FROM ORDERS;              -- 주문 10건 전부 삭제
SELECT COUNT(*) FROM ORDERS;     -- 0건. 지워진 것처럼 보인다

ROLLBACK;                        -- 취소!
SELECT COUNT(*) FROM ORDERS;     -- 10건. 다 살아났다
```

- COMMIT 하기 전이라면 ROLLBACK 으로 되돌릴 수 있다.
- **COMMIT 한 뒤에는 되돌릴 수 없다.**
- DDL(CREATE/DROP/TRUNCATE 등)은 실행하는 순간 자동 COMMIT 된다. 그래서 DROP 은 ROLLBACK 이 안 됐던 것.

### 4.1 COMMIT 을 안 하면 생기는 일

내가 INSERT 하고 COMMIT 을 안 하면:

- 다른 접속(다른 사람, 혹은 Java 애플리케이션)에서는 그 데이터가 **안 보인다.**
- 내가 수정한 행은 **잠금(lock)** 이 걸려서, 다른 사람이 같은 행을 수정하려 하면 하염없이 기다리게 된다.

"분명 INSERT 했는데 프로그램에서 조회가 안 돼요"의 원인 대부분이 COMMIT 누락이다.  
**DML 을 실행했으면 COMMIT(또는 ROLLBACK)으로 마무리한다** — 지금은 이것만 기억하자. 트랜잭션의 전체 그림은 ch12 에서 다룬다.

> SQL Developer 툴바에도 COMMIT/ROLLBACK 버튼이 있다. 창을 닫을 때 미커밋 변경이 있으면 물어본다.

---

## 정리

- `INSERT INTO 테이블 (컬럼들) VALUES (값들)` — 컬럼 지정 방식을 쓰자. 문자는 `'작은따옴표'`, 날짜는 `TO_DATE`.
- `UPDATE 테이블 SET 컬럼=값 WHERE 조건`, `DELETE FROM 테이블 WHERE 조건` — **WHERE 를 빼면 전체가 대상.**
- DML 은 COMMIT 전까지 임시 상태. `COMMIT` 확정 / `ROLLBACK` 취소. DDL 은 자동 COMMIT.

---

## 문제

실습테이블 기준. **문제를 풀기 전에** 실습테이블.sql 을 다시 실행해 초기화하고 시작하자. (본문 예제에서 이미 50번 부서를 넣었을 수 있어, 초기화하지 않으면 1번에서 PK 중복 `ORA-00001` 이 난다.) 1~5번은 순서대로 이어서 푼다.

1. DEPT 테이블에 부서번호 50, 부서명 '총무팀', 지역 '대전' 인 부서를 컬럼 지정 방식으로 INSERT 하시오.
2. EMP 테이블에 아래 사원을 INSERT 하시오. (보너스와 상사는 없음)
   - 사원번호 1014, 이름 '아이유', 직급 '사원', 입사일 2025-03-02, 월급 3100, 이메일 'lee.ji@daehan.com', 부서 50
3. 1014번 사원의 월급을 3400으로, 직급을 '대리' 로 한 번의 UPDATE 로 변경하시오.
4. 영업팀(30번 부서) 사원 전원의 보너스를 지금의 2배로 올리는 UPDATE 를 작성하시오. 실행 전에 대상이 몇 명인지 SELECT 로 먼저 확인하시오. (보너스가 NULL 인 사원은 어떻게 되는지도 관찰)
5. 1014번 사원과 50번 부서를 삭제하시오. (어느 쪽을 먼저 지워야 하는가?)
6. `DELETE FROM EMP;` 를 실행한 직후 다시 되돌리려면 어떤 명령을 써야 하는가? 만약 그 사이에 COMMIT 을 했다면?

→ [답안 보기](문제답안/ch3_문제답안.md)
