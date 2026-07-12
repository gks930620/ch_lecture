---
layout: default
title: ch5_문제답안
---

# ch5 문제답안

[← ch5 문서로](../ch5_단일행함수.md)

## 1. 이메일 도메인 추출

```sql
SELECT EMP_NAME,
       EMAIL,
       SUBSTR(EMAIL, INSTR(EMAIL, '@') + 1) AS 도메인
FROM   EMP;
```

- `INSTR(EMAIL, '@')` 로 @ 의 위치를 찾고, 그 **+1 위치부터 끝까지** SUBSTR 로 자른다.
- 전 사원 모두 `daehan.com` 이 나온다.

## 2. 가격 콤마 표시

```sql
SELECT PRODUCT_NAME,
       TO_CHAR(PRICE, '999,999,999') AS 가격
FROM   PRODUCT;
```

노트북이 `1,500,000` 으로 나오면 성공. 형식의 9 개수는 표시할 최대 자릿수보다 넉넉하면 된다.  
(9 형식은 앞에 공백이 붙어 오른쪽 정렬된다. 앞 공백이 신경 쓰이면 본문 §4.2 처럼 `FM999,999,999` 를 쓰면 된다.)

## 3. 입사일 형식 + 근속년수

```sql
SELECT EMP_NAME,
       TO_CHAR(HIRE_DATE, 'YYYY"년" MM"월" DD"일"')          AS 입사일,
       TRUNC(MONTHS_BETWEEN(SYSDATE, HIRE_DATE) / 12)        AS 근속년수
FROM   EMP;
```

- 형식 안에 한글을 넣을 때는 `"년"` 처럼 큰따옴표로 감싼다.
- 개월수 ÷ 12 를 TRUNC 로 버림하면 만 년수가 된다.

## 4. NULL 안전한 연봉 계산

```sql
SELECT EMP_NAME,
       SALARY,
       BONUS,
       SALARY * 12 + NVL(BONUS, 0) AS 연봉
FROM   EMP;
```

`NVL(BONUS, 0)` 이 핵심. 이게 없으면 보너스 NULL 인 사원의 연봉이 NULL 로 나온다.

## 5. CASE 로 부서 표시

```sql
SELECT EMP_NAME,
       DEPT_ID,
       CASE DEPT_ID
           WHEN 10 THEN '본사'
           WHEN 20 THEN '판교캠퍼스'
           WHEN 30 THEN '부산지사'
           ELSE '미배정'
       END AS 근무지
FROM   EMP;
```

부서가 NULL 인 신동엽은 어떤 WHEN 과도 일치하지 않으므로(NULL 은 = 비교 불가) ELSE 로 떨어져 '미배정' 이 된다.

## 6. 주민번호 앞자리

```sql
SELECT SUBSTR('990305-1234567', 1, 6) AS 생년월일 FROM DUAL;
-- 990305
```
