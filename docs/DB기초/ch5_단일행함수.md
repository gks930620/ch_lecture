---
layout: default
title: ch5_단일행함수
description: 문자/숫자/날짜 함수, TO_CHAR·TO_DATE 변환, NVL, CASE와 DECODE
---

# ch5_단일행함수

---

## 학습 목표
- 문자 함수(SUBSTR, REPLACE, ...)와 숫자 함수(ROUND, TRUNC, ...)를 쓸 수 있다.
- 날짜를 계산하고, TO_CHAR / TO_DATE 로 날짜↔문자를 변환할 수 있다.
- NVL 로 NULL 을 다른 값으로 바꿀 수 있다.
- CASE / DECODE 로 값에 따라 다른 결과를 만들 수 있다.

**단일행 함수**는 행 하나당 결과 하나를 돌려주는 함수다. (12명 조회하면 12개 결과)  
행 여러 개를 묶어 하나를 내는 그룹 함수(ch6)와 구분된다.

---

## 1. 문자 함수

| 함수 | 하는 일 | 예 → 결과 |
|------|---------|-----------|
| `UPPER(s)` / `LOWER(s)` | 대문자로 / 소문자로 | `UPPER('abc')` → `ABC` |
| `LENGTH(s)` | 글자 수 | `LENGTH('데이터')` → 3 |
| `SUBSTR(s, m, n)` | m번째부터 n글자 자르기 | `SUBSTR('데이터베이스', 1, 3)` → `데이터` |
| `INSTR(s, x)` | x 가 몇 번째에 있나 (없으면 0) | `INSTR('kim.cs@daehan.com', '@')` → 7 |
| `REPLACE(s, a, b)` | a 를 b 로 바꾸기 | `REPLACE('010-1234', '-', '')` → `0101234` |
| `TRIM(s)` | 양쪽 공백 제거 | `TRIM('  ab ')` → `ab` |
| `LPAD(s, n, c)` / `RPAD` | 왼쪽/오른쪽을 c 로 채워 n글자로 | `LPAD('7', 3, '0')` → `007` |

```sql
-- SUBSTR 위치는 1부터 시작한다 (Java 는 0부터, 여기는 1부터!)
SELECT SUBSTR('Oracle SQL', 1, 6) FROM DUAL;   -- Oracle
SELECT SUBSTR('Oracle SQL', 8) FROM DUAL;      -- SQL (끝까지)
SELECT SUBSTR('Oracle SQL', -3) FROM DUAL;     -- SQL (음수 = 뒤에서부터)
```

조합 예제 — 이메일에서 아이디 부분만 뽑기:

```sql
SELECT EMAIL,
       SUBSTR(EMAIL, 1, INSTR(EMAIL, '@') - 1) AS 아이디
FROM   EMP;
-- kim.cs@daehan.com → kim.cs
```

이름 마스킹 — 가운데 글자 가리기:

```sql
SELECT EMP_NAME,
       SUBSTR(EMP_NAME, 1, 1) || '*' || SUBSTR(EMP_NAME, 3) AS 마스킹
FROM   EMP;
-- 김철수 → 김*수
```

---

## 2. 숫자 함수

| 함수 | 하는 일 | 예 → 결과 |
|------|---------|-----------|
| `ROUND(n, i)` | 소수점 i자리로 반올림 | `ROUND(123.456, 1)` → 123.5 |
| `TRUNC(n, i)` | 소수점 i자리 아래 버림 | `TRUNC(123.456, 1)` → 123.4 |
| `CEIL(n)` / `FLOOR(n)` | 올림 / 내림 | `CEIL(1.1)` → 2, `FLOOR(1.9)` → 1 |
| `MOD(a, b)` | a 를 b 로 나눈 나머지 | `MOD(10, 3)` → 1 |

```sql
-- i 가 음수면 정수 자리에서 동작한다
SELECT ROUND(12567, -2) FROM DUAL;   -- 12600 (십의 자리에서 반올림)
SELECT TRUNC(12567, -3) FROM DUAL;   -- 12000

-- 사원번호가 짝수인 사원
SELECT * FROM EMP WHERE MOD(EMP_ID, 2) = 0;
```

---

## 3. 날짜 함수와 날짜 계산

### 3.1 날짜 ± 숫자

DATE 에 숫자를 더하고 빼면 **일(day)** 단위로 움직인다.

```sql
SELECT SYSDATE          AS 오늘,
       SYSDATE + 7      AS 일주일뒤,
       SYSDATE - 1      AS 어제
FROM   DUAL;

-- 날짜 - 날짜 = 일수 차이
SELECT EMP_NAME,
       TRUNC(SYSDATE - HIRE_DATE) AS 근무일수
FROM   EMP;
```

### 3.2 날짜 전용 함수

| 함수 | 하는 일 |
|------|---------|
| `SYSDATE` | 현재 날짜+시각 |
| `ADD_MONTHS(d, n)` | n개월 뒤 (일수 아님, 달 단위) |
| `MONTHS_BETWEEN(d1, d2)` | 두 날짜의 개월 차이 |
| `LAST_DAY(d)` | 그 달의 마지막 날 |
| `NEXT_DAY(d, '월요일')` | d 이후 첫 번째 해당 요일 |
| `TRUNC(d)` | 시각을 00:00:00 으로 잘라냄 |

```sql
-- 사원별 근속 개월/년수
SELECT EMP_NAME,
       HIRE_DATE,
       TRUNC(MONTHS_BETWEEN(SYSDATE, HIRE_DATE))      AS 근속개월,
       TRUNC(MONTHS_BETWEEN(SYSDATE, HIRE_DATE) / 12) AS 근속년수
FROM   EMP;
```

> `TRUNC(d)` 는 날짜 비교에서 중요하다. DATE 는 시분초까지 있어서,  
> `WHERE HIRE_DATE = TO_DATE('2024-07-01','YYYY-MM-DD')` 는 그날 00:00:00 인 행만 잡는다.  
> "그 날짜인 행"을 원하면 `WHERE TRUNC(HIRE_DATE) = TO_DATE('2024-07-01','YYYY-MM-DD')`.

---

## 4. 변환 함수 — TO_CHAR / TO_NUMBER / TO_DATE

### 4.1 TO_CHAR(날짜, 형식) — 날짜를 문자로

```sql
SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD')          FROM DUAL;  -- 2026-07-05 (예시. 실행일마다 다름)
SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') FROM DUAL; -- 2026-07-05 14:30:00 (예시)
SELECT TO_CHAR(SYSDATE, 'YYYY"년" MM"월" DD"일" DY') FROM DUAL;  -- 2026년 07월 05일 일 (예시)
```

주요 형식 문자: `YYYY`(연) `MM`(월) `DD`(일) `HH24`(24시간制 시) `MI`(분) `SS`(초) `DAY/DY`(요일)

### 4.2 TO_CHAR(숫자, 형식) — 숫자를 문자로

```sql
SELECT TO_CHAR(1500000, '999,999,999') FROM DUAL;   -- 1,500,000
SELECT PRODUCT_NAME, TO_CHAR(PRICE, 'L999,999,999') AS 가격 FROM PRODUCT;  -- ₩1,500,000
```

> `9` 형식은 자리에 값이 없으면 공백으로 두고, 부호(±)가 들어갈 자리까지 앞에 비워 둔다. 그래서 결과 앞에 공백이 붙는다.  
> 앞 공백까지 없애려면 형식 앞에 `FM` 을 붙인다: `TO_CHAR(1500000, 'FM999,999,999')` → `1,500,000` (공백 없음).

### 4.3 TO_DATE(문자, 형식) — 문자를 날짜로

```sql
SELECT * FROM EMP
WHERE  HIRE_DATE >= TO_DATE('2020-01-01', 'YYYY-MM-DD');
```

### 4.4 암시적 변환은 피하자

`WHERE HIRE_DATE >= '2020-01-01'` 처럼 문자열을 바로 쓰면 오라클이 알아서 변환을 시도하는데,  
**DB 설정(NLS_DATE_FORMAT)에 따라 되기도 하고 오류가 나기도 한다.** 날짜는 항상 TO_DATE 로 명시하자.

---

## 5. NULL 처리 함수

| 함수 | 하는 일 |
|------|---------|
| `NVL(a, b)` | a 가 NULL 이면 b, 아니면 a |
| `NVL2(a, b, c)` | a 가 NULL 이 아니면 b, NULL 이면 c |
| `COALESCE(a, b, c, ...)` | 앞에서부터 처음 만나는 NOT NULL 값 (표준 SQL) |

ch4 에서 봤던 "월급 + 보너스가 NULL" 문제를 해결할 수 있다.

```sql
SELECT EMP_NAME,
       SALARY,
       BONUS,
       SALARY + NVL(BONUS, 0) AS 실수령
FROM   EMP;
-- 보너스 NULL 인 사원도 이제 월급 그대로 나온다
```

```sql
SELECT EMP_NAME,
       NVL2(BONUS, '보너스있음', '보너스없음') AS 구분
FROM   EMP;
```

---

## 6. 조건 분기 — CASE 와 DECODE

### 6.1 CASE (표준, 권장)

```sql
-- 값 비교형
SELECT EMP_NAME, JOB,
       CASE JOB
           WHEN '사장' THEN '임원'
           WHEN '부장' THEN '관리자'
           WHEN '과장' THEN '관리자'
           ELSE '실무자'
       END AS 등급
FROM   EMP;

-- 조건형 (범위 등 자유로운 조건)
SELECT EMP_NAME, SALARY,
       CASE
           WHEN SALARY >= 6000 THEN '고연봉'
           WHEN SALARY >= 4000 THEN '중간'
           ELSE '초봉'
       END AS 급여등급
FROM   EMP;
```

- 위에서부터 차례로 검사, 처음 맞는 WHEN 의 값이 결과.
- ELSE 를 생략하면 아무것도 안 맞을 때 NULL.

### 6.2 DECODE (오라클 전용)

```sql
SELECT EMP_NAME,
       DECODE(JOB, '사장', '임원',
                   '부장', '관리자',
                   '과장', '관리자',
                           '실무자') AS 등급
FROM   EMP;
```

`DECODE(대상, 비교1, 결과1, 비교2, 결과2, ..., 기본값)`.  
= 비교만 가능하고 오라클에서만 동작한다. 옛날 코드에서 많이 보이므로 읽을 줄은 알아야 하고, 새로 쓸 때는 CASE 를 권장한다.

---

## 정리

- 문자: `SUBSTR`(1부터 시작!), `INSTR`, `REPLACE`, `LENGTH`, `LPAD`. 숫자: `ROUND`, `TRUNC`, `MOD`.
- 날짜 ± 숫자는 일 단위, 달 단위는 `ADD_MONTHS` / `MONTHS_BETWEEN`. 시각 제거는 `TRUNC(날짜)`.
- 날짜↔문자 변환은 `TO_CHAR(날짜, 형식)` / `TO_DATE(문자, 형식)`. 암시적 변환에 의존하지 말 것.
- `NVL(값, 대체값)` 으로 NULL 계산 문제를 해결. 조건 분기는 `CASE`(권장) / `DECODE`(오라클 전용).

---

## 문제

실습테이블 기준.

1. EMP 에서 이름, 이메일, 그리고 이메일의 `@` 뒤 도메인 부분(예: `daehan.com`)을 `도메인` 이라는 제목으로 조회하시오.
2. PRODUCT 에서 상품명과, 가격을 `1,500,000` 형태(천 단위 콤마)로 조회하시오.
3. EMP 에서 이름, 입사일(`2016년 07월 15일` 형태), 근속년수(정수)를 조회하시오.
4. EMP 에서 이름, 월급, 보너스, 그리고 `월급*12 + 보너스` 로 계산한 연봉을 조회하시오. 보너스가 NULL 인 사원도 연봉이 NULL 이 되지 않게 하시오.
5. EMP 에서 이름, 부서번호, 그리고 부서번호가 10이면 '본사', 20이면 '판교캠퍼스', 30이면 '부산지사', 그 외(NULL 포함)면 '미배정' 이 나오게 CASE 로 조회하시오.
6. 주민번호 형식 문자열 `'990305-1234567'` 에서 생년월일 6자리만 잘라내는 SQL 을 DUAL 로 작성하시오.

→ [답안 보기](문제답안/ch5_문제답안.md)
