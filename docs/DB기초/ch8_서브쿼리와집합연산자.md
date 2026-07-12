---
layout: default
title: ch8_서브쿼리와집합연산자
description: 단일행/다중행 서브쿼리, IN/EXISTS, 인라인뷰, UNION/UNION ALL/INTERSECT/MINUS
---

# ch8_서브쿼리와집합연산자

---

## 학습 목표
- 단일행 / 다중행 서브쿼리를 구분해서 쓸 수 있다.
- IN, EXISTS 를 서브쿼리와 함께 쓸 수 있다.
- FROM 절 서브쿼리(인라인 뷰)를 쓸 수 있다.
- UNION / UNION ALL / INTERSECT / MINUS 를 쓸 수 있다.

---

## 1. 서브쿼리란

"**평균 월급보다** 많이 받는 사원은?" — 이 질문은 두 단계다.

1. 평균 월급을 구한다 → `SELECT AVG(SALARY) FROM EMP` → 4675
2. 그 값보다 큰 사원을 찾는다 → `SELECT * FROM EMP WHERE SALARY > 4675`

이 두 단계를 한 문장으로 합친 것이 서브쿼리다. **쿼리 안의 괄호 친 쿼리.**

```sql
SELECT EMP_NAME, SALARY
FROM   EMP
WHERE  SALARY > (SELECT AVG(SALARY) FROM EMP);
```

안쪽 괄호가 먼저 실행되어 값이 되고, 바깥 쿼리가 그 값을 이용한다.

---

## 2. 단일행 서브쿼리

서브쿼리 결과가 **1행 1컬럼**(값 하나)일 때. `=`, `>`, `<` 같은 일반 비교 연산자를 쓴다.

```sql
-- 송강호와 같은 부서인 사원
SELECT EMP_NAME, DEPT_ID
FROM   EMP
WHERE  DEPT_ID = (SELECT DEPT_ID FROM EMP WHERE EMP_NAME = '송강호');

-- 월급이 가장 높은 사원 (누가 얼마 받는지)
SELECT EMP_NAME, SALARY
FROM   EMP
WHERE  SALARY = (SELECT MAX(SALARY) FROM EMP);
```

주의: 서브쿼리가 여러 행을 돌려주면 오류다.

```sql
SELECT * FROM EMP
WHERE  DEPT_ID = (SELECT DEPT_ID FROM EMP WHERE JOB = '과장');
-- ORA-01427: single-row subquery returns more than one row  (과장이 3명이라 부서가 여러 개)
```

---

## 3. 다중행 서브쿼리 — IN, ANY, ALL

서브쿼리 결과가 **여러 행**일 수 있으면 전용 연산자를 쓴다.

```sql
-- 주문(판매) 실적이 있는 사원
SELECT EMP_NAME
FROM   EMP
WHERE  EMP_ID IN (SELECT EMP_ID FROM ORDERS);

-- 서울에 있는 부서 소속 사원
SELECT EMP_NAME, DEPT_ID
FROM   EMP
WHERE  DEPT_ID IN (SELECT DEPT_ID FROM DEPT WHERE LOC = '서울');
```

| 연산자 | 의미 |
|--------|------|
| `IN (서브쿼리)` | 목록 중 하나와 같으면 참 |
| `NOT IN (서브쿼리)` | 목록 어느 것과도 다르면 참 (아래 주의!) |
| `> ANY (서브쿼리)` | 하나보다라도 크면 참 (= 최솟값보다 크면) |
| `> ALL (서브쿼리)` | 전부보다 크면 참 (= 최댓값보다 크면) |

ANY/ALL 은 시험에는 나오지만 실무에서는 MAX/MIN 서브쿼리로 쓰는 경우가 많다.

> **NOT IN + NULL 함정**: 서브쿼리 결과에 NULL 이 하나라도 있으면 NOT IN 은 전체가 0건이 된다.  
> 예를 들어 `WHERE EMP_ID NOT IN (SELECT MGR_ID FROM EMP)` 는 MGR_ID 에 NULL 이 있어서 0건.  
> `SELECT MGR_ID FROM EMP WHERE MGR_ID IS NOT NULL` 처럼 NULL 을 걸러 줘야 한다.

---

## 4. EXISTS — 존재 여부만 확인

```sql
-- 주문이 하나라도 있는 상품
SELECT PRODUCT_NAME
FROM   PRODUCT P
WHERE  EXISTS (SELECT 1 FROM ORDERS O WHERE O.PRODUCT_ID = P.PRODUCT_ID);

-- 한 번도 안 팔린 상품 (ch7 에서 LEFT JOIN 으로 풀었던 문제의 다른 풀이)
SELECT PRODUCT_NAME
FROM   PRODUCT P
WHERE  NOT EXISTS (SELECT 1 FROM ORDERS O WHERE O.PRODUCT_ID = P.PRODUCT_ID);
```

- 바깥 행 하나마다 "조건에 맞는 행이 서브쿼리에 존재하는가?"만 본다. `SELECT 1` 의 1은 아무 의미 없는 자리채움이다.
- 바깥 테이블(P)의 컬럼을 서브쿼리 안에서 참조하는 것을 **상관 서브쿼리**라 한다.
- IN 과 결과가 같은 경우가 많다. NOT 계열에서는 NULL 함정이 없는 `NOT EXISTS` 가 안전하다.

---

## 5. FROM 절 서브쿼리 — 인라인 뷰

서브쿼리를 FROM 에 넣으면 **그 결과를 임시 테이블처럼** 쓸 수 있다.

```sql
-- 부서별 평균월급을 구해놓고, 그중 4500 이상인 부서에 부서명을 붙이기
SELECT D.DEPT_NAME, T.평균월급
FROM   (SELECT DEPT_ID, ROUND(AVG(SALARY)) AS 평균월급
        FROM   EMP
        GROUP BY DEPT_ID) T
JOIN   DEPT D ON T.DEPT_ID = D.DEPT_ID
WHERE  T.평균월급 >= 4500;
```

괄호 안 결과(부서별 평균)에 `T` 라는 별칭을 붙이고, 진짜 테이블처럼 조인/조건을 걸었다.  
집계 결과를 한 번 더 가공할 때 매우 자주 쓰는 패턴이고, ch11 의 순위 매기기(TOP-N)에서도 핵심이 된다.

### 5.1 SELECT 절 서브쿼리 (스칼라 서브쿼리)

```sql
SELECT E.EMP_NAME,
       (SELECT D.DEPT_NAME FROM DEPT D WHERE D.DEPT_ID = E.DEPT_ID) AS 부서명
FROM   EMP E;
```

SELECT 절 안의 서브쿼리는 행마다 **값 하나**를 돌려줘야 한다. 조인으로도 같은 결과를 만들 수 있으며, 보통 조인이 우선이다.

---

## 6. 집합 연산자 — UNION, INTERSECT, MINUS

두 SELECT 의 **결과를 세로로 합치거나 비교**한다. (조인은 가로로 붙이고, 집합 연산자는 세로로 쌓는다)

```sql
SELECT 문장1
UNION          -- 합집합 (중복 제거)
SELECT 문장2;
```

| 연산자 | 의미 |
|--------|------|
| `UNION` | 합집합, 중복 행은 한 번만 |
| `UNION ALL` | 합집합, 중복 그대로 (빠름) |
| `INTERSECT` | 교집합 (양쪽에 다 있는 행) |
| `MINUS` | 차집합 (첫 결과에서 두 번째 결과를 뺌) |

```sql
-- 사원 이름과 고객 이름을 명단 하나로
SELECT EMP_NAME AS NAME FROM EMP
UNION
SELECT CUSTOMER_NAME FROM ORDERS;

-- 상사이기도 한 사원 (사원번호 목록 ∩ 상사번호 목록)
SELECT EMP_ID FROM EMP
INTERSECT
SELECT MGR_ID FROM EMP;

-- 아무의 상사도 아닌 사원
SELECT EMP_ID FROM EMP
MINUS
SELECT MGR_ID FROM EMP;
```

규칙:

- 두 SELECT 의 **컬럼 개수와 자료형 순서가 같아야** 한다.
- 최종 컬럼 이름은 **첫 번째 SELECT** 를 따른다.
- ORDER BY 는 **전체 결과 맨 마지막에 한 번만** 쓸 수 있다.
- 중복 제거가 필요 없으면 `UNION ALL` 이 빠르다 (중복 검사 비용이 없음).

> 참고: 표준 SQL 의 MINUS 는 `EXCEPT` 다. 오라클도 21c부터 EXCEPT 를 지원하지만, 오라클 코드에서는 MINUS 가 관례다.

---

## 정리

- 서브쿼리 = 괄호 친 안쪽 쿼리 결과를 바깥 쿼리가 이용. 값 하나면 `=`/`>` 비교, 여러 행이면 `IN`.
- `NOT IN` 은 서브쿼리에 NULL 이 있으면 0건이 되는 함정이 있다 → `NOT EXISTS` 가 안전.
- FROM 절 서브쿼리(인라인 뷰)는 집계 결과를 임시 테이블처럼 다시 가공할 때 쓴다.
- 집합 연산자는 결과를 세로로: `UNION`(중복 제거) / `UNION ALL`(그대로, 빠름) / `INTERSECT` / `MINUS`.

---

## 문제

실습테이블 기준.

1. 전체 평균 월급보다 월급이 적은 사원의 이름과 월급을 조회하시오.
2. '송강호' 와 같은 직급인 사원을 모두 조회하시오. (송강호 본인 제외)
3. 가장 비싼 상품의 상품명과 가격을 서브쿼리로 조회하시오.
4. 상품을 하나라도 판매한 사원의 이름을 IN 을 사용해 조회하시오. 그리고 같은 결과를 EXISTS 로도 작성하시오.
5. 판매 실적이 전혀 없는 사원의 이름을 조회하시오. (NOT IN 으로 풀 때 주의할 점이 있는지 확인 — EMP_ID 에 NULL 이 있는가? ORDERS.EMP_ID 에는?)
6. 부서별 최고 월급자(부서번호, 이름, 월급)를 조회하시오.  
   (힌트: 부서별 MAX(SALARY) 를 구하는 인라인 뷰와 (DEPT_ID, SALARY) 조인 또는 IN)
7. 집합 연산자로 다음 둘을 구하시오. (기준: PRODUCT 전체 상품번호)
   - 주문된 적이 **없는** 상품번호 (MINUS 사용)
   - 주문된 적이 **있는** 상품번호 (INTERSECT 사용)

→ [답안 보기](문제답안/ch8_문제답안.md)
