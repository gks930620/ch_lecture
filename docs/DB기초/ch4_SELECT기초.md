---
layout: default
title: ch4_SELECT기초
description: SELECT, WHERE 조건(비교/LIKE/IN/BETWEEN/NULL), ORDER BY, DISTINCT, 별칭
---

# ch4_SELECT기초

---

## 학습 목표
- SELECT 로 원하는 컬럼만 골라 조회할 수 있다.
- WHERE 에서 비교/논리 연산자, LIKE, IN, BETWEEN, IS NULL 을 쓸 수 있다.
- ORDER BY 로 정렬하고, DISTINCT 로 중복을 제거할 수 있다.
- NULL 이 일반 값과 어떻게 다른지 안다.

SQL 의 절반은 SELECT 다. 실무에서 가장 많이 쓰고, 이후 모든 챕터의 기반이 된다.

---

## 1. SELECT 기본

```sql
SELECT 컬럼1, 컬럼2, ...   -- 무엇을 (열 선택)
FROM   테이블명            -- 어디서
WHERE  조건                -- 어떤 행만 (행 선택, 생략 가능)
ORDER BY 정렬기준;         -- 어떤 순서로 (생략 가능)
```

```sql
-- 모든 컬럼 조회 (* = 전체 컬럼)
SELECT * FROM EMP;

-- 원하는 컬럼만
SELECT EMP_NAME, SALARY FROM EMP;

-- 컬럼으로 계산도 가능 (연봉 = 월급 × 12)
SELECT EMP_NAME, SALARY, SALARY * 12 FROM EMP;
```

### 1.1 별칭 (Alias)

계산식 컬럼은 제목이 `SALARY*12` 처럼 지저분하게 나온다. `AS` 로 별칭을 붙인다.

```sql
SELECT EMP_NAME AS 이름,
       SALARY   AS 월급,
       SALARY * 12 AS 연봉
FROM   EMP;
```

- `AS` 는 생략 가능: `SALARY * 12 연봉`
- 별칭에 공백/특수문자를 넣으려면 큰따옴표: `SALARY * 12 AS "연봉 (원)"`

### 1.2 DISTINCT — 중복 제거

```sql
-- 사원들의 직급 종류만 보고 싶다
SELECT DISTINCT JOB FROM EMP;
```

### 1.3 DUAL — 계산 연습용 테이블

오라클은 FROM 이 필수라서, 테이블 없이 값만 확인할 때 쓰는 1행짜리 더미 테이블 `DUAL` 이 있다.

```sql
SELECT 1 + 1 FROM DUAL;        -- 2
SELECT SYSDATE FROM DUAL;      -- 현재 날짜/시각
```

함수 연습할 때(ch5) 계속 쓰게 된다.

### 1.4 문자열 연결 `||`

```sql
SELECT EMP_NAME || '(' || JOB || ')' AS 사원 FROM EMP;
-- 김철수(사장), 이영희(부장), ...
```

---

## 2. WHERE — 행 골라내기

### 2.1 비교 연산자

```sql
SELECT * FROM EMP WHERE SALARY >= 5000;      -- 월급 5000 이상
SELECT * FROM EMP WHERE JOB = '과장';        -- 직급이 과장 (문자열 비교. 대소문자/철자 정확히)
SELECT * FROM EMP WHERE DEPT_ID <> 20;       -- 20번 부서가 아닌 (!= 도 같은 뜻). 부서가 NULL 인 신동엽은 여기 안 나온다 (이유는 §2.5)
SELECT * FROM EMP WHERE HIRE_DATE < TO_DATE('2020-01-01','YYYY-MM-DD');  -- 2020년 이전 입사
```

주의: 같음 비교는 `=` 하나다. (Java 의 `==` 아님)

### 2.2 논리 연산자 AND / OR / NOT

```sql
-- 개발팀(20) 이면서 월급 5000 이상
SELECT * FROM EMP WHERE DEPT_ID = 20 AND SALARY >= 5000;

-- 부장 또는 과장
SELECT * FROM EMP WHERE JOB = '부장' OR JOB = '과장';

-- 인사팀(10) 이거나 영업팀(30) 이면서 월급 3000 이상?  괄호에 따라 뜻이 달라진다
SELECT * FROM EMP WHERE (DEPT_ID = 10 OR DEPT_ID = 30) AND SALARY >= 3000;
```

AND 가 OR 보다 먼저 계산된다. 섞어 쓸 때는 **괄호로 의도를 명확히** 하자.

### 2.3 BETWEEN, IN

```sql
-- 월급 3000 ~ 5000 (양쪽 끝 포함)
SELECT * FROM EMP WHERE SALARY BETWEEN 3000 AND 5000;

-- 직급이 부장/과장/대리 중 하나 (OR 여러 개를 짧게)
SELECT * FROM EMP WHERE JOB IN ('부장', '과장', '대리');

-- 반대: 목록에 없는 것
SELECT * FROM EMP WHERE JOB NOT IN ('사원', '인턴');
```

### 2.4 LIKE — 패턴 검색

| 기호 | 의미 |
|------|------|
| `%` | 0글자 이상 아무 문자 |
| `_` | 정확히 1글자 |

```sql
SELECT * FROM EMP WHERE EMP_NAME LIKE '김%';     -- 김으로 시작
SELECT * FROM EMP WHERE EMAIL LIKE '%kim%';      -- 이메일에 kim 포함
SELECT * FROM EMP WHERE EMP_NAME LIKE '_수_';    -- 세 글자이고 두 번째 글자가 '수' (임수정)
SELECT * FROM EMP WHERE EMP_NAME LIKE '%수';     -- '수' 로 끝나는 이름 (김철수, 박민수, 오달수)
```

게시판 검색 기능이 대부분 `WHERE TITLE LIKE '%검색어%'` 다.

### 2.5 NULL 비교 — IS NULL / IS NOT NULL

NULL 은 "값이 없음"이라는 특별한 상태다. 0도 아니고 빈 문자열도 아니다.  
**NULL 은 `=` 로 비교할 수 없다.**

```sql
SELECT * FROM EMP WHERE BONUS = NULL;       -- 0건! (문법 오류는 아니지만 결과가 '알 수 없음'이라 한 행도 통과 못 함)
SELECT * FROM EMP WHERE BONUS IS NULL;      -- 보너스 없는 사원 (올바른 방법)
SELECT * FROM EMP WHERE BONUS IS NOT NULL;  -- 보너스 있는 사원
```

NULL 이 낀 계산 결과도 NULL 이다.

```sql
SELECT EMP_NAME, SALARY + BONUS FROM EMP;
-- 보너스가 NULL 인 사원은 (월급 + NULL) = NULL 로 나온다. 해결법은 ch5 의 NVL.
```

---

## 3. ORDER BY — 정렬

```sql
SELECT EMP_NAME, SALARY FROM EMP ORDER BY SALARY;         -- 오름차순 (ASC, 기본값)
SELECT EMP_NAME, SALARY FROM EMP ORDER BY SALARY DESC;    -- 내림차순

-- 2차 정렬: 부서 오름차순, 같은 부서 안에서는 월급 내림차순
SELECT DEPT_ID, EMP_NAME, SALARY
FROM   EMP
ORDER BY DEPT_ID ASC, SALARY DESC;
```

- ORDER BY 는 항상 **문장 맨 마지막**에 쓴다.
- 별칭으로도 정렬 가능: `SELECT SALARY*12 AS 연봉 ... ORDER BY 연봉 DESC`
- NULL 은 오름차순에서 **맨 뒤**에 온다(오라클 기준). `ORDER BY BONUS NULLS FIRST` 로 바꿀 수 있다.
- ORDER BY 를 안 쓰면 순서는 **보장되지 않는다.** "어제는 이 순서로 나왔는데"는 우연이다.

---

## 4. 실행 순서 감각

SQL 은 쓰는 순서와 실제 처리 순서가 다르다.

```
FROM (어느 테이블에서) → WHERE (행 거르고) → SELECT (열 고르고) → ORDER BY (정렬)
```

이 감각이 있으면 "WHERE 에서는 SELECT 별칭을 못 쓴다" 같은 규칙이 자연스럽게 이해된다.  
(WHERE 가 SELECT 보다 먼저 실행되니까, 그 시점엔 별칭이 아직 없다.)

---

## 정리

- `SELECT 컬럼 FROM 테이블 WHERE 조건 ORDER BY 정렬` — 열 선택은 SELECT, 행 선택은 WHERE.
- 조건: 비교(`=`, `<>`, `>=`...), `AND/OR`, `BETWEEN a AND b`, `IN (목록)`, `LIKE '패턴'`.
- NULL 은 `IS NULL / IS NOT NULL` 로만 비교. NULL 이 낀 계산은 NULL.
- 정렬은 `ORDER BY 컬럼 [ASC|DESC]`, 여러 기준은 쉼표. 순서가 중요하면 반드시 ORDER BY.

---

## 문제

실습테이블 기준.

1. EMP 에서 이름, 직급, 월급, 연봉(월급×12)을 조회하시오. 컬럼 제목은 각각 이름/직급/월급/연봉 으로 나오게 하시오.
2. 월급이 3000 이상 5500 이하인 사원의 이름과 월급을, 월급 많은 순으로 조회하시오. (BETWEEN 사용)
3. 개발팀(20) 또는 영업팀(30) 소속이면서 직급이 '사원' 인 사람을 조회하시오. (IN 사용)
4. 이메일이 `lee` 로 시작하는 사원의 이름과 이메일을 조회하시오.
5. 보너스가 없는(NULL) 사원의 이름, 월급, 보너스를 조회하시오.
6. ORDERS 에서 주문을 넣은 적 있는 고객 이름을 중복 없이 조회하시오.
7. (생각해보기) `WHERE BONUS <> 100` 의 결과에 보너스가 NULL 인 사원이 포함될까? 실행해 보고 이유를 설명하시오.

→ [답안 보기](문제답안/ch4_문제답안.md)
