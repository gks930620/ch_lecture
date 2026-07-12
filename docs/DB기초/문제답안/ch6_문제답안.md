---
layout: default
title: ch6_문제답안
---

# ch6 문제답안

[← ch6 문서로](../ch6_그룹함수와GROUPBY.md)

## 1. 전체 집계

```sql
SELECT COUNT(*)    AS 사원수,
       MAX(SALARY) AS 최고월급,
       MIN(SALARY) AS 최저월급,
       SUM(SALARY) AS 월급총액
FROM   EMP;
```

결과: 사원수 12, 최고 9000, 최저 2400, 총액 56100.

## 2. 직급별 집계

```sql
SELECT JOB,
       COUNT(*)           AS 인원,
       TRUNC(AVG(SALARY)) AS 평균월급
FROM   EMP
GROUP BY JOB;
```

| JOB | 인원 | 평균월급 |
|-----|------|----------|
| 사장 | 1 | 9000 |
| 부장 | 2 | 6350 |
| 과장 | 3 | 5233 |
| 대리 | 1 | 4100 |
| 사원 | 4 | 3050 |
| 인턴 | 1 | 2400 |

> 위 표는 보기 좋게 정렬해 놓았지만, 쿼리에 `ORDER BY` 가 없으므로 **실제 출력 순서는 보장되지 않는다.** 순서가 달라도 값이 같으면 정답이다. 순서를 고정하고 싶으면 `ORDER BY JOB` 등을 덧붙인다.

## 3. 부서별 보너스 받는 사원 수

```sql
SELECT DEPT_ID,
       COUNT(BONUS) AS 보너스받는사원수
FROM   EMP
GROUP BY DEPT_ID;
```

`COUNT(컬럼)` 은 NULL 을 세지 않는 특성을 그대로 이용한다.  
결과: 10번 부서 1명, 20번 3명, 30번 3명, NULL 부서 0명.

## 4. 상품별 총 주문수량 + HAVING

```sql
SELECT PRODUCT_ID,
       SUM(QUANTITY) AS 총수량
FROM   ORDERS
GROUP BY PRODUCT_ID
HAVING SUM(QUANTITY) >= 4;
```

상품별 총수량은 2001(노트북) 4, 2002(모니터) 3, 2003(키보드) 8, 2004(의자) 4, 2005(책상) 3.  
HAVING 을 통과하는 것은 **2001, 2003, 2004** 세 상품이다.

## 5. 입사 연도별 + HAVING + 정렬

```sql
SELECT TO_CHAR(HIRE_DATE, 'YYYY') AS 입사연도,
       COUNT(*)                   AS 입사자수
FROM   EMP
GROUP BY TO_CHAR(HIRE_DATE, 'YYYY')
HAVING COUNT(*) >= 2
ORDER BY 입사연도;
```

결과: 2018년 2명(박민수, 유재석), 2022년 2명(강호동, 정우성).

## 6. GROUP BY 오류 고치기

```sql
SELECT DEPT_ID, JOB, COUNT(*)
FROM   EMP
GROUP BY DEPT_ID;      -- ORA-00979
```

**오류 이유**: `JOB` 이 GROUP BY 에 없다. 부서별로 묶인 그룹 안에는 여러 직급이 섞여 있어서, JOB 자리에 어떤 값을 내보낼지 정할 수 없다.

고치는 방법은 의도에 따라 두 가지:

```sql
-- (1) 부서+직급 조합별로 세고 싶었다면: GROUP BY 에 JOB 추가
SELECT DEPT_ID, JOB, COUNT(*)
FROM   EMP
GROUP BY DEPT_ID, JOB;

-- (2) 부서별로만 세고 싶었다면: SELECT 에서 JOB 제거
SELECT DEPT_ID, COUNT(*)
FROM   EMP
GROUP BY DEPT_ID;
```
