---
layout: default
title: ch6_그룹함수와GROUPBY
description: COUNT/SUM/AVG/MAX/MIN, GROUP BY, HAVING, WHERE와 HAVING의 차이
---

# ch6_그룹함수와GROUPBY

---

## 학습 목표
- COUNT, SUM, AVG, MAX, MIN 을 쓸 수 있다.
- GROUP BY 로 그룹별 집계를 낼 수 있다.
- WHERE 와 HAVING 의 차이를 설명할 수 있다.
- "GROUP BY 에 없는 컬럼은 SELECT 에 못 쓴다" 규칙을 이해한다.

**그룹 함수(집계 함수)**는 여러 행을 묶어서 **결과 하나**를 돌려준다.  
"사원이 몇 명이지?", "부서별 평균 월급은?" 같은 질문이 전부 그룹 함수다.

---

## 1. 그룹 함수 다섯 가지

| 함수 | 하는 일 |
|------|---------|
| `COUNT(*)` | 행 개수 |
| `SUM(컬럼)` | 합계 |
| `AVG(컬럼)` | 평균 |
| `MAX(컬럼)` / `MIN(컬럼)` | 최댓값 / 최솟값 |

```sql
SELECT COUNT(*)                AS 사원수,
       SUM(SALARY)             AS 월급총액,
       ROUND(AVG(SALARY), 1)   AS 평균월급,
       MAX(SALARY)             AS 최고월급,
       MIN(SALARY)             AS 최저월급
FROM   EMP;
```

12행이 들어가서 **1행이 나온다.** 이게 그룹 함수의 핵심이다.

### 1.1 그룹 함수와 NULL

**그룹 함수는 컬럼을 인자로 받을 때 NULL 을 무시하고 계산한다.** (단 `COUNT(*)` 는 컬럼이 아니라 행 자체를 세므로 예외 — NULL 이 있어도 행이면 센다)

```sql
SELECT COUNT(*)      AS 전체행수,      -- 12 (행 자체를 센다)
       COUNT(BONUS)  AS 보너스있는사원, -- 7  (NULL 제외하고 센다)
       AVG(BONUS)    AS 보너스평균      -- NULL 제외 7명의 평균
FROM   EMP;
```

주의할 함정 — "전체 사원 기준 평균 보너스"를 원한다면:

```sql
SELECT AVG(BONUS)          FROM EMP;   -- 보너스 받는 사람들만의 평균
SELECT AVG(NVL(BONUS, 0))  FROM EMP;   -- 전원 기준 평균 (안 받는 사람 = 0)
```

둘은 결과가 다르다. 어느 쪽이 맞는지는 **업무 요구사항**이 정한다.

### 1.2 COUNT(DISTINCT 컬럼)

```sql
-- 주문한 고객이 몇 명인가 (같은 고객 중복 제거)
SELECT COUNT(DISTINCT CUSTOMER_NAME) FROM ORDERS;
```

---

## 2. GROUP BY — 그룹별로 나눠서 집계

"부서**별** 평균 월급" — 이 "~별" 이 나오면 GROUP BY 다.

```sql
SELECT DEPT_ID,
       COUNT(*)              AS 인원,
       ROUND(AVG(SALARY))    AS 평균월급
FROM   EMP
GROUP BY DEPT_ID
ORDER BY DEPT_ID;
```

결과 (부서가 NULL 인 신동엽도 하나의 그룹이 된다):

| DEPT_ID | 인원 | 평균월급 |
|---------|------|----------|
| 10 | 3 | 5833 |
| 20 | 4 | 4750 |
| 30 | 4 | 4300 |
| (NULL) | 1 | 2400 |

동작 순서: EMP 12행을 → DEPT_ID 값별로 묶고 → 묶음마다 COUNT/AVG 를 계산한다.

### 2.1 가장 많이 하는 실수

```sql
SELECT DEPT_ID, EMP_NAME, AVG(SALARY)   -- 오류!
FROM   EMP
GROUP BY DEPT_ID;
-- ORA-00979: not a GROUP BY expression
```

부서별로 묶으면 20번 부서 그룹 안에 사원이 4명이다. `EMP_NAME` 자리에 누구 이름을 내보내야 할지 정할 수 없다.  
그래서 규칙: **SELECT 에는 (1) GROUP BY 에 쓴 컬럼, (2) 그룹 함수 — 이 둘만 올 수 있다.**

### 2.2 여러 컬럼으로 그룹

```sql
-- 부서별 + 직급별 인원과 월급 합계
SELECT DEPT_ID, JOB, COUNT(*) AS 인원, SUM(SALARY) AS 합계
FROM   EMP
GROUP BY DEPT_ID, JOB
ORDER BY DEPT_ID, JOB;
```

(DEPT_ID, JOB) 조합이 같은 행끼리 한 그룹이 된다.

### 2.3 표현식으로 그룹

```sql
-- 입사 연도별 입사자 수
SELECT TO_CHAR(HIRE_DATE, 'YYYY') AS 입사연도, COUNT(*) AS 입사자수
FROM   EMP
GROUP BY TO_CHAR(HIRE_DATE, 'YYYY')
ORDER BY 입사연도;
```

SELECT 에 쓴 표현식을 GROUP BY 에도 **똑같이** 써 준다.

---

## 3. HAVING — 그룹에 조건 걸기

"부서별 평균 월급을 구하되, **평균이 4500 이상인 부서만**" — 그룹 결과에 조건을 걸 때는 HAVING.

```sql
SELECT DEPT_ID, ROUND(AVG(SALARY)) AS 평균월급
FROM   EMP
GROUP BY DEPT_ID
HAVING AVG(SALARY) >= 4500;
```

WHERE 에 그룹 함수를 쓰면 오류가 난다:

```sql
SELECT DEPT_ID, AVG(SALARY)
FROM   EMP
WHERE  AVG(SALARY) >= 4500     -- ORA-00934: group function is not allowed here
GROUP BY DEPT_ID;
```

### 3.1 WHERE vs HAVING

| | WHERE | HAVING |
|---|-------|--------|
| 거르는 대상 | **행** (그룹으로 묶기 전) | **그룹** (묶은 후) |
| 그룹 함수 사용 | 불가 | 가능 |
| 실행 시점 | GROUP BY 이전 | GROUP BY 이후 |

둘을 같이 쓰는 예 — "'사원' 직급은 빼고, 부서별 평균 월급이 5000 이상인 부서":

```sql
SELECT DEPT_ID, ROUND(AVG(SALARY)) AS 평균월급
FROM   EMP
WHERE  JOB <> '사원'                -- 1) 행 필터: 사원 직급 제외
GROUP BY DEPT_ID                    -- 2) 부서별로 묶고
HAVING AVG(SALARY) >= 5000          -- 3) 그룹 필터
ORDER BY 평균월급 DESC;             -- 4) 정렬
```

실행 순서가 곧 읽는 순서다:

```
FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY
```

---

## 정리

- 그룹 함수(COUNT/SUM/AVG/MAX/MIN)는 여러 행 → 결과 1개. **컬럼을 인자로 주면 NULL 은 계산에서 빠진다(단 `COUNT(*)` 는 예외).**
- "~별" 은 `GROUP BY`. SELECT 에는 GROUP BY 컬럼과 그룹 함수만 쓸 수 있다.
- 행 조건은 WHERE(그룹 전), 그룹 조건은 HAVING(그룹 후).
- 실행 순서: FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY.

---

## 문제

실습테이블 기준.

1. EMP 전체의 사원 수, 최고 월급, 최저 월급, 월급 총액을 한 번에 조회하시오.
2. 직급(JOB)별 인원 수와 평균 월급(소수점 버림)을 조회하시오.
3. 부서별로 보너스를 받는 사원 수를 조회하시오. (COUNT 의 NULL 처리 특성을 이용)
4. ORDERS 에서 상품(PRODUCT_ID)별 총 주문 수량을 구하고, 총 수량이 4개 이상인 상품만 조회하시오.
5. 입사 연도별 입사자 수를 구하되, 2명 이상 입사한 연도만, 연도 오름차순으로 조회하시오.
6. (생각해보기) 다음 SQL 이 오류가 나는 이유를 설명하고 바르게 고치시오.
   ```sql
   SELECT DEPT_ID, JOB, COUNT(*)
   FROM   EMP
   GROUP BY DEPT_ID;
   ```

→ [답안 보기](문제답안/ch6_문제답안.md)
