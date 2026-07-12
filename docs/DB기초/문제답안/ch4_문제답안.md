---
layout: default
title: ch4_문제답안
---

# ch4 문제답안

[← ch4 문서로](../ch4_SELECT기초.md)

## 1. 별칭을 붙인 조회

```sql
SELECT EMP_NAME    AS 이름,
       JOB         AS 직급,
       SALARY      AS 월급,
       SALARY * 12 AS 연봉
FROM   EMP;
```

## 2. BETWEEN + 정렬

```sql
SELECT EMP_NAME, SALARY
FROM   EMP
WHERE  SALARY BETWEEN 3000 AND 5500
ORDER BY SALARY DESC;
```

결과 **7명**: 유재석(5500), 박민수(5200), 송강호(5000), 최지은(4100), 정우성(3200), 임수정(3100), 강호동(3000).  
BETWEEN 은 양쪽 끝값(3000, 5500)을 **포함**한다 — 그래서 강호동(딱 3000)과 유재석(딱 5500)이 들어온다.

## 3. IN + AND

```sql
SELECT *
FROM   EMP
WHERE  DEPT_ID IN (20, 30)
  AND  JOB = '사원';
```

결과 3명: 정우성(20), 임수정(30), 오달수(30).

## 4. LIKE

```sql
SELECT EMP_NAME, EMAIL
FROM   EMP
WHERE  EMAIL LIKE 'lee%';
```

결과: 이영희(lee.yh@daehan.com).

## 5. IS NULL

```sql
SELECT EMP_NAME, SALARY, BONUS
FROM   EMP
WHERE  BONUS IS NULL;
```

결과 5명: 김철수, 최지은, 임수정, 유재석, 신동엽.  
`BONUS = NULL` 로 쓰면 0건이 나온다는 점을 꼭 확인하자.

## 6. DISTINCT

```sql
SELECT DISTINCT CUSTOMER_NAME FROM ORDERS;
```

결과 4명: 나민호, 고윤아, 장서준, 문세윤.

## 7. NULL 과 <> 비교

```sql
SELECT EMP_NAME, BONUS FROM EMP WHERE BONUS <> 100;
```

**보너스가 NULL 인 사원은 포함되지 않는다.** 결과는 보너스가 있으면서 100이 아닌 6명뿐이다(500, 300, 200, 400, 1000, 250).

이유: NULL 과의 비교(`NULL <> 100`)는 참도 거짓도 아닌 "알 수 없음(unknown)"이고, WHERE 는 참인 행만 통과시킨다.  
"100이 아닌 사원(없는 사람 포함)"을 원하면:

```sql
WHERE BONUS <> 100 OR BONUS IS NULL
-- 또는
WHERE NVL(BONUS, 0) <> 100
```
