---
layout: default
title: ch8_문제답안
---

# ch8 문제답안

[← ch8 문서로](../ch8_서브쿼리와집합연산자.md)

## 1. 평균보다 적게 받는 사원

```sql
SELECT EMP_NAME, SALARY
FROM   EMP
WHERE  SALARY < (SELECT AVG(SALARY) FROM EMP);
```

평균은 4675. 결과 6명: 최지은(4100), 정우성(3200), 임수정(3100), 강호동(3000), 오달수(2900), 신동엽(2400).

## 2. 송강호와 같은 직급

```sql
SELECT EMP_NAME, JOB
FROM   EMP
WHERE  JOB = (SELECT JOB FROM EMP WHERE EMP_NAME = '송강호')
  AND  EMP_NAME <> '송강호';
```

송강호는 과장. 결과 2명: 박민수, 유재석.  
(단일행 서브쿼리 — 송강호가 1명이라 가능. 동명이인이 있을 수 있는 실무라면 IN 을 쓰거나 사원번호로 조회한다)

## 3. 가장 비싼 상품

```sql
SELECT PRODUCT_NAME, PRICE
FROM   PRODUCT
WHERE  PRICE = (SELECT MAX(PRICE) FROM PRODUCT);
```

결과: 노트북, 1500000.

## 4. 판매 실적 있는 사원 — IN 과 EXISTS

```sql
-- IN
SELECT EMP_NAME
FROM   EMP
WHERE  EMP_ID IN (SELECT EMP_ID FROM ORDERS);

-- EXISTS
SELECT EMP_NAME
FROM   EMP E
WHERE  EXISTS (SELECT 1 FROM ORDERS O WHERE O.EMP_ID = E.EMP_ID);
```

결과 3명: 송강호(1007), 임수정(1008), 오달수(1009).

## 5. 판매 실적 없는 사원

```sql
-- NOT EXISTS (권장)
SELECT EMP_NAME
FROM   EMP E
WHERE  NOT EXISTS (SELECT 1 FROM ORDERS O WHERE O.EMP_ID = E.EMP_ID);

-- NOT IN 으로 쓸 경우
SELECT EMP_NAME
FROM   EMP
WHERE  EMP_ID NOT IN (SELECT EMP_ID FROM ORDERS WHERE EMP_ID IS NOT NULL);
```

결과 9명 (판매 3인방을 뺀 전원).

현재 데이터에서는 ORDERS.EMP_ID 에 NULL 이 없어서 `NOT IN (SELECT EMP_ID FROM ORDERS)` 도 동작한다.  
하지만 ORDERS.EMP_ID 는 NULL 이 허용된 컬럼이라(담당사원 없는 주문이 생길 수 있다), NULL 이 한 건이라도 생기는 순간 NOT IN 결과가 통째로 0건이 된다. **NOT 계열은 NOT EXISTS 또는 IS NOT NULL 필터를 습관화**하자.

## 6. 부서별 최고 월급자

```sql
-- 인라인 뷰 + 조인
SELECT E.DEPT_ID, E.EMP_NAME, E.SALARY
FROM   EMP E
JOIN   (SELECT DEPT_ID, MAX(SALARY) AS MAX_SAL
        FROM   EMP
        GROUP BY DEPT_ID) T
  ON   E.DEPT_ID = T.DEPT_ID AND E.SALARY = T.MAX_SAL;

-- 또는 다중 컬럼 IN
SELECT DEPT_ID, EMP_NAME, SALARY
FROM   EMP
WHERE  (DEPT_ID, SALARY) IN (SELECT DEPT_ID, MAX(SALARY)
                             FROM   EMP
                             GROUP BY DEPT_ID);
```

결과: 10번 김철수(9000), 20번 이영희(6500), 30번 한가인(6200).  
부서가 NULL 인 신동엽은 `NULL = NULL` 비교가 성립하지 않아 빠진다. (ch11 의 윈도우 함수로 풀면 NULL 부서까지 다룰 수 있다)

## 7. 집합 연산자

```sql
-- 주문된 적 없는 상품번호
SELECT PRODUCT_ID FROM PRODUCT
MINUS
SELECT PRODUCT_ID FROM ORDERS;
-- 결과: 2006 (마우스)

-- 주문된 적 있는 상품번호
SELECT PRODUCT_ID FROM PRODUCT
INTERSECT
SELECT PRODUCT_ID FROM ORDERS;
-- 결과: 2001, 2002, 2003, 2004, 2005
```

MINUS / INTERSECT 는 자동으로 중복을 제거해 주므로 ORDERS 에 같은 상품 주문이 여러 건 있어도 상품번호는 한 번씩만 나온다.
