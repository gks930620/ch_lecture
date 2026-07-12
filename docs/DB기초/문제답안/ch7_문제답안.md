---
layout: default
title: ch7_문제답안
---

# ch7 문제답안

[← ch7 문서로](../ch7_조인.md)

## 1. 사원 + 부서 INNER JOIN

```sql
SELECT E.EMP_NAME, E.JOB, D.DEPT_NAME, D.LOC
FROM   EMP E
JOIN   DEPT D ON E.DEPT_ID = D.DEPT_ID;
```

11행. 부서가 NULL 인 신동엽은 빠진다.

## 2. LEFT JOIN 으로 전원 포함

```sql
SELECT E.EMP_NAME, E.JOB, D.DEPT_NAME, D.LOC
FROM   EMP E
LEFT JOIN DEPT D ON E.DEPT_ID = D.DEPT_ID;
```

12행. 신동엽의 DEPT_NAME, LOC 는 NULL 로 나온다.

## 3. 사원 없는 부서

```sql
SELECT D.DEPT_NAME
FROM   DEPT D
LEFT JOIN EMP E ON E.DEPT_ID = D.DEPT_ID
WHERE  E.EMP_ID IS NULL;
```

결과: 마케팅팀.  
DEPT 를 기준(왼쪽)으로 LEFT JOIN 하면 사원이 없는 부서는 EMP 쪽 컬럼이 전부 NULL 로 채워진다. 그 행만 남기면 된다.  
(`FROM EMP E RIGHT JOIN DEPT D ...` 로 써도 같다)

## 4. 주문 상세 (조인 + 계산 컬럼)

```sql
SELECT O.ORDER_ID,
       O.CUSTOMER_NAME,
       P.PRODUCT_NAME,
       P.PRICE * O.QUANTITY AS 주문금액
FROM   ORDERS O
JOIN   PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID
ORDER BY O.ORDER_ID;
```

| ORDER_ID | 고객 | 상품 | 주문금액 |
|----------|------|------|----------|
| 5001 | 나민호 | 노트북 | 1,500,000 |
| 5002 | 고윤아 | 모니터 | 700,000 |
| 5003 | 나민호 | 키보드 | 240,000 |
| 5004 | 장서준 | 노트북 | 3,000,000 |
| 5005 | 고윤아 | 책상 | 450,000 |
| 5006 | 문세윤 | 의자 | 880,000 |
| 5007 | 나민호 | 모니터 | 350,000 |
| 5008 | 장서준 | 키보드 | 400,000 |
| 5009 | 고윤아 | 노트북 | 1,500,000 |
| 5010 | 문세윤 | 책상 | 900,000 |

## 5. 부서명별 사원 수 (0 포함)

```sql
SELECT D.DEPT_NAME,
       COUNT(E.EMP_ID) AS 사원수
FROM   DEPT D
LEFT JOIN EMP E ON E.DEPT_ID = D.DEPT_ID
GROUP BY D.DEPT_NAME;
```

결과: 인사팀 3, 개발팀 4, 영업팀 4, **마케팅팀 0**.

`COUNT(*)` 로 하면 마케팅팀이 **1** 로 나온다. LEFT JOIN 결과에 "마케팅팀 + 전부 NULL" 인 행이 1개 존재하고, `COUNT(*)` 는 그 행 자체를 세기 때문이다.  
`COUNT(E.EMP_ID)` 는 NULL 을 세지 않으므로(ch6) 0 이 된다.

## 6. 셀프 조인 + NVL

```sql
SELECT E.EMP_NAME              AS 사원,
       NVL(M.EMP_NAME, '(없음)') AS 상사
FROM   EMP E
LEFT JOIN EMP M ON E.MGR_ID = M.EMP_ID
ORDER BY E.EMP_ID;
```

김철수(사장)와 신동엽(인턴)은 MGR_ID 가 NULL 이라 상사가 '(없음)' 으로 나온다.  
INNER JOIN 으로 쓰면 이 두 명이 결과에서 아예 빠지므로 LEFT JOIN 이어야 한다.
