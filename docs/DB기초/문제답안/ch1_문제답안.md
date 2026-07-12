---
layout: default
title: ch1_문제답안
---

# ch1 문제답안

[← ch1 문서로](../ch1_DB와SQL시작하기.md)

## 1. 테이블/행/열/기본키 구분

- (a) 홍길동 회원 1명의 전체 정보 → **행(row)** — 데이터 1건
- (b) 모든 회원의 이메일을 모아 놓은 세로줄 → **열(column)**
- (c) 회원을 유일하게 구분하는 회원번호 → **기본키(primary key)**

## 2. SQL 분류

| 분류 | 명령어 |
|------|--------|
| DDL (정의) | CREATE, DROP, ALTER |
| DML (조작) | SELECT, INSERT, UPDATE, DELETE |
| TCL (트랜잭션 제어) | COMMIT, ROLLBACK |

> SELECT 를 따로 DQL 로 분류하는 책도 있다. 이 강의에서는 DML 에 포함시킨다.

## 3. PRODUCT 전체 조회

```sql
SELECT * FROM PRODUCT;
```

**6건** 이 조회된다. (노트북, 모니터, 키보드, 의자, 책상, 마우스)
