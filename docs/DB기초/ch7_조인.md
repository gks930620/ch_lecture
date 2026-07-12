---
layout: default
title: ch7_조인
description: INNER JOIN, OUTER JOIN, 셀프조인, 3개 테이블 조인, 오라클 (+) 표기
---

# ch7_조인

---

## 학습 목표
- 조인이 왜 필요한지 설명할 수 있다.
- ANSI 표준 문법으로 INNER JOIN / LEFT·RIGHT OUTER JOIN 을 쓸 수 있다.
- 3개 이상 테이블 조인, 셀프 조인을 쓸 수 있다.
- 오라클 전용 (+) 표기를 읽을 수 있다.

---

## 1. 조인이 왜 필요한가

EMP 를 조회하면 부서가 `20` 이라는 번호로만 나온다. 사람이 원하는 건 `개발팀` 이라는 이름이다.  
부서 이름은 DEPT 테이블에 있다. → **두 테이블을 연결해서 한 번에 조회**하는 것이 조인이다.

그럼 애초에 EMP 에 부서명을 같이 저장하면 되지 않나?

| EMP_NAME | DEPT_NAME |
|----------|-----------|
| 이영희 | 개발팀 |
| 박민수 | 개발팀 |
| 최지은 | 개발팀 |

이러면 부서명이 바뀔 때 사원 수만큼 UPDATE 해야 하고, 하나라도 빠지면 데이터가 어긋난다.  
그래서 관계형 DB 는 **중복을 없애도록 테이블을 쪼개서 저장**(정규화)하고, **조회할 때 조인으로 다시 합친다.**

---

## 2. INNER JOIN — 기본 조인

```sql
SELECT E.EMP_NAME, E.SALARY, D.DEPT_NAME
FROM   EMP E
JOIN   DEPT D ON E.DEPT_ID = D.DEPT_ID;
```

- `EMP E` — 테이블에도 별칭을 붙인다. 이후 `E.컬럼` 으로 사용.
- `ON E.DEPT_ID = D.DEPT_ID` — **조인 조건**: 사원의 부서번호와 부서의 부서번호가 같은 행끼리 연결.
- 양쪽 다 DEPT_ID 컬럼이 있으므로, 그냥 `DEPT_ID` 라고 쓰면 "column ambiguously defined" 오류. **어느 테이블 것인지 항상 명시**한다.
- `INNER JOIN` 이라고 써도 같다 (INNER 는 생략 가능).

결과는 **11명**이다. 12명이 아니다!  
부서가 NULL 인 신동엽(1012)은 `ON` 조건을 만족하는 DEPT 행이 없어서 결과에서 **빠진다.**  
마찬가지로 사원이 없는 마케팅팀(40)도 안 나온다. — INNER JOIN 은 **양쪽 다 짝이 있는 행만** 남긴다.

### 2.1 조인 + WHERE

조인 결과도 보통 테이블처럼 WHERE / GROUP BY / ORDER BY 를 다 쓸 수 있다.

```sql
-- 부산에서 근무하는 사원
SELECT E.EMP_NAME, D.DEPT_NAME, D.LOC
FROM   EMP E
JOIN   DEPT D ON E.DEPT_ID = D.DEPT_ID
WHERE  D.LOC = '부산';

-- 부서이름별 평균 월급 (조인 + GROUP BY)
SELECT D.DEPT_NAME, ROUND(AVG(E.SALARY)) AS 평균월급
FROM   EMP E
JOIN   DEPT D ON E.DEPT_ID = D.DEPT_ID
GROUP BY D.DEPT_NAME;
```

ON 은 "두 테이블을 어떻게 연결하나", WHERE 는 "연결된 결과에서 어떤 행을 남기나" — 역할이 다르다.

---

## 3. OUTER JOIN — 짝이 없어도 남기기

"부서 없는 사원도 포함해서 전부 보여줘" → OUTER JOIN.

```sql
-- LEFT OUTER JOIN: 왼쪽(EMP) 은 짝이 없어도 전부 나온다
SELECT E.EMP_NAME, D.DEPT_NAME
FROM   EMP E
LEFT JOIN DEPT D ON E.DEPT_ID = D.DEPT_ID;
-- 12행. 신동엽의 DEPT_NAME 은 NULL 로 채워진다
```

```sql
-- RIGHT OUTER JOIN: 오른쪽(DEPT) 기준. 사원 없는 마케팅팀도 나온다
SELECT E.EMP_NAME, D.DEPT_NAME
FROM   EMP E
RIGHT JOIN DEPT D ON E.DEPT_ID = D.DEPT_ID;
-- 12행. 마케팅팀 행의 EMP_NAME 이 NULL
```

```sql
-- FULL OUTER JOIN: 양쪽 다 (신동엽도, 마케팅팀도)
SELECT E.EMP_NAME, D.DEPT_NAME
FROM   EMP E
FULL JOIN DEPT D ON E.DEPT_ID = D.DEPT_ID;
-- 13행
```

- `LEFT JOIN` = `LEFT OUTER JOIN` (OUTER 생략 가능).
- 실무에서는 LEFT JOIN 을 압도적으로 많이 쓴다. "기준 테이블을 FROM(왼쪽)에 두고 LEFT JOIN" 으로 통일하면 헷갈리지 않는다.

활용 예 — **한 번도 안 팔린 상품 찾기**:

```sql
SELECT P.PRODUCT_NAME, O.ORDER_ID
FROM   PRODUCT P
LEFT JOIN ORDERS O ON P.PRODUCT_ID = O.PRODUCT_ID
WHERE  O.ORDER_ID IS NULL;
-- 마우스 (주문과 짝이 안 맞아 NULL 로 채워진 행 = 주문이 없는 상품)
```

---

## 4. 3개 테이블 조인

JOIN 을 이어 붙이면 된다. "주문번호, 상품명, 담당사원명, 고객명, 수량":

```sql
SELECT O.ORDER_ID,
       P.PRODUCT_NAME,
       E.EMP_NAME     AS 담당사원,
       O.CUSTOMER_NAME,
       O.QUANTITY
FROM   ORDERS O
JOIN   PRODUCT P ON O.PRODUCT_ID = P.PRODUCT_ID
JOIN   EMP     E ON O.EMP_ID     = E.EMP_ID
ORDER BY O.ORDER_ID;
```

읽는 법: ORDERS 를 기준으로 → 상품 정보를 붙이고 → 사원 정보를 붙인다.  
테이블이 몇 개든 "기준 테이블에서 시작해 하나씩 붙인다"는 감각은 같다.

---

## 5. 셀프 조인 — 같은 테이블끼리

EMP 의 MGR_ID(상사 사원번호)는 같은 EMP 테이블의 EMP_ID 를 가리킨다.  
"사원 이름과 그 상사의 이름"을 나란히 보려면, **EMP 를 두 번 등장시켜** 조인한다.

```sql
SELECT E.EMP_NAME AS 사원,
       M.EMP_NAME AS 상사
FROM   EMP E
LEFT JOIN EMP M ON E.MGR_ID = M.EMP_ID
ORDER BY E.EMP_ID;
```

- 같은 테이블이지만 별칭 `E`(사원 역할), `M`(상사 역할)으로 서로 다른 테이블처럼 취급한다.
- LEFT JOIN 인 이유: 상사(MGR_ID)가 없는 사원 — 사장 김철수(1001)와 아직 상사 배정 전인 신동엽(1012) — 도 결과에 나오게 하려고. INNER JOIN 으로 하면 이 둘이 빠진다.

---

## 6. 오라클 전용 (+) 표기 — 읽을 줄만 알면 된다

옛날 오라클 코드에서는 이런 문법을 자주 본다.

```sql
-- 아래 둘은 같은 뜻이다
SELECT E.EMP_NAME, D.DEPT_NAME
FROM   EMP E, DEPT D
WHERE  E.DEPT_ID = D.DEPT_ID(+);   -- 오라클 전용: (+) 붙은 쪽이 "부족한 쪽" → LEFT JOIN

SELECT E.EMP_NAME, D.DEPT_NAME
FROM   EMP E
LEFT JOIN DEPT D ON E.DEPT_ID = D.DEPT_ID;
```

- `FROM A, B WHERE A.x = B.x` — WHERE 에 조인 조건을 쓰는 옛 방식 (INNER JOIN 과 동일).
- `(+)` 가 붙은 쪽이 NULL 로 채워지는 쪽이다.
- 유지보수 중인 옛 코드에 많아서 **읽을 줄은 알아야** 하지만, 새로 작성할 때는 ANSI JOIN 문법을 쓰자.

> 참고: `FROM A, B` 에서 WHERE 조인 조건을 빼먹으면 A 행수 × B 행수의 모든 조합(카티션 곱)이 나온다.  
> EMP × DEPT = 12 × 4 = 48행. 조인 결과가 이상하게 뻥튀기됐다면 조인 조건 누락부터 의심하자.

---

## 정리

- 조인 = 쪼개 놓은 테이블을 조회 시점에 다시 연결. `JOIN 테이블 ON 연결조건`.
- INNER JOIN 은 양쪽에 짝이 있는 행만, OUTER JOIN 은 짝 없는 행도 NULL 로 채워서 남긴다. 기본은 LEFT JOIN.
- "짝이 없는 행 찾기" = LEFT JOIN + `WHERE 오른쪽키 IS NULL`.
- 3개 조인은 JOIN 을 이어 붙이고, 셀프 조인은 같은 테이블에 별칭 두 개.
- 옛 코드의 `WHERE A.x = B.x(+)` 는 OUTER JOIN 이다. 읽을 줄 알되, 새 코드는 ANSI 문법으로.

---

## 문제

실습테이블 기준.

1. 사원 이름, 직급, 부서명, 부서 지역을 조회하시오. (부서 없는 사원은 안 나와도 됨)
2. 1번을 고쳐서 부서 없는 사원(신동엽)도 나오게 하시오.
3. 사원이 한 명도 없는 부서의 이름을 조인으로 찾으시오.
4. 주문번호, 고객명, 상품명, 상품가격 × 수량(제목: 주문금액)을 주문번호 순으로 조회하시오.
5. 부서명별 사원 수를 조회하되, 사원이 없는 마케팅팀도 0으로 나오게 하시오.  
   (힌트: DEPT 기준 LEFT JOIN + `COUNT(E.EMP_ID)` — `COUNT(*)` 로 하면 왜 안 되는지도 생각해 보자)
6. 사원 이름과 상사 이름을 조회하되, 상사가 없는 사원은 상사 이름 대신 '(없음)' 으로 나오게 하시오. (셀프 조인 + NVL)

→ [답안 보기](문제답안/ch7_문제답안.md)
