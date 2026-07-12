---
layout: default
title: ch3_문제답안
---

# ch3 문제답안

[← ch3 문서로](../ch3_데이터입력수정삭제.md)

## 1. 부서 INSERT

```sql
INSERT INTO DEPT (DEPT_ID, DEPT_NAME, LOC) VALUES (50, '총무팀', '대전');
```

## 2. 사원 INSERT

```sql
INSERT INTO EMP (EMP_ID, EMP_NAME, JOB, HIRE_DATE, SALARY, EMAIL, DEPT_ID)
VALUES (1014, '아이유', '사원', TO_DATE('2025-03-02', 'YYYY-MM-DD'), 3100, 'lee.ji@daehan.com', 50);
```

- 보너스(BONUS)와 상사(MGR_ID)는 컬럼 목록에서 빼면 NULL 이 들어간다.
- 1번의 50번 부서를 먼저 넣어야 한다. 안 그러면 FK 오류(ORA-02291).

## 3. 월급 + 직급 동시 변경

```sql
UPDATE EMP
SET    SALARY = 3400, JOB = '대리'
WHERE  EMP_ID = 1014;
```

SET 에 쉼표로 여러 컬럼을 나열하면 UPDATE 한 번으로 처리된다.

## 4. 영업팀 보너스 2배

```sql
-- 먼저 대상 확인 (4명: 한가인, 송강호, 임수정, 오달수)
SELECT EMP_NAME, BONUS FROM EMP WHERE DEPT_ID = 30;

UPDATE EMP
SET    BONUS = BONUS * 2
WHERE  DEPT_ID = 30;
```

관찰 포인트: 보너스가 NULL 인 임수정은 `NULL * 2 = NULL` 이라 **그대로 NULL** 이다.  
즉 보너스가 없던 사원은 이 UPDATE 로도 여전히 NULL 이다. 이런 사원까지 특정 값으로 챙기려면 NULL 을 먼저 다른 값으로 바꿔 줘야 하는데, 그 방법(NVL 함수)은 ch5 에서 배운다.

## 5. 사원/부서 삭제

```sql
DELETE FROM EMP  WHERE EMP_ID  = 1014;   -- 자식(사원) 먼저
DELETE FROM DEPT WHERE DEPT_ID = 50;     -- 그 다음 부모(부서)
COMMIT;
```

부서를 먼저 지우려 하면 1014번 사원이 참조 중이라 ORA-02292 오류가 난다.  
**자식(참조하는 쪽) → 부모(참조되는 쪽)** 순서로 지운다.

## 6. 전체 삭제 되돌리기

```sql
ROLLBACK;
```

- COMMIT 전이므로 ROLLBACK 으로 전부 되돌릴 수 있다.
- 그 사이에 **COMMIT 을 했다면 되돌릴 수 없다.** (백업/플래시백 같은 별도 수단이 없는 한)  
  DELETE 든 UPDATE 든, COMMIT 은 "이제 확정" 이라는 뜻이므로 항상 확인 후에 실행하자.
