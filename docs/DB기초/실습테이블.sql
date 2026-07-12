--------------------------------------------------------------------------------
-- DB기초 강의 실습용 테이블 생성 스크립트 (Oracle 기준)
--
-- 사용 방법
--   1. SQL Developer(또는 SQL*Plus)에서 실습용 계정으로 접속한다.
--   2. 이 파일 전체를 열어서 실행한다. (SQL Developer: F5 = 스크립트 실행)
--   3. 마지막의 SELECT 로 데이터가 잘 들어갔는지 확인한다.
--
-- 여러 번 실행해도 되도록, 시작할 때 기존 테이블을 삭제(DROP)하고 다시 만든다.
-- 처음 실행할 때는 "table or view does not exist" 오류가 떠도 정상이다. (지울 게 없어서)
--------------------------------------------------------------------------------

-- 기존 테이블 삭제 (자식 테이블부터 삭제해야 FK 오류가 안 난다)
DROP TABLE ORDERS;
DROP TABLE PRODUCT;
DROP TABLE EMP;
DROP TABLE DEPT;

--------------------------------------------------------------------------------
-- 1. DEPT (부서)
--------------------------------------------------------------------------------
CREATE TABLE DEPT (
    DEPT_ID    NUMBER(4)     CONSTRAINT PK_DEPT PRIMARY KEY,  -- 부서번호
    DEPT_NAME  VARCHAR2(30)  NOT NULL,                        -- 부서명
    LOC        VARCHAR2(30)                                   -- 지역
);

INSERT INTO DEPT VALUES (10, '인사팀', '서울');
INSERT INTO DEPT VALUES (20, '개발팀', '판교');
INSERT INTO DEPT VALUES (30, '영업팀', '부산');
INSERT INTO DEPT VALUES (40, '마케팅팀', '서울');   -- 소속 사원이 없는 부서 (외부조인 실습용)

--------------------------------------------------------------------------------
-- 2. EMP (사원)
--------------------------------------------------------------------------------
CREATE TABLE EMP (
    EMP_ID     NUMBER(4)     CONSTRAINT PK_EMP PRIMARY KEY,   -- 사원번호
    EMP_NAME   VARCHAR2(20)  NOT NULL,                        -- 사원이름
    JOB        VARCHAR2(20),                                  -- 직급
    MGR_ID     NUMBER(4),                                     -- 직속상사의 사원번호 (셀프조인 실습용)
    HIRE_DATE  DATE,                                          -- 입사일
    SALARY     NUMBER(7),                                     -- 월급
    BONUS      NUMBER(7),                                     -- 보너스 (없는 사원은 NULL)
    EMAIL      VARCHAR2(40),                                  -- 회사 이메일 (문자함수 실습용)
    DEPT_ID    NUMBER(4),                                     -- 소속 부서번호
    CONSTRAINT FK_EMP_DEPT FOREIGN KEY (DEPT_ID) REFERENCES DEPT(DEPT_ID)
);

INSERT INTO EMP VALUES (1001, '김철수', '사장', NULL, TO_DATE('2015-03-01','YYYY-MM-DD'), 9000, NULL, 'kim.cs@daehan.com',   10);
INSERT INTO EMP VALUES (1002, '이영희', '부장', 1001, TO_DATE('2016-07-15','YYYY-MM-DD'), 6500,  500, 'lee.yh@daehan.com',   20);
INSERT INTO EMP VALUES (1003, '박민수', '과장', 1002, TO_DATE('2018-01-10','YYYY-MM-DD'), 5200,  300, 'park.ms@daehan.com',  20);
INSERT INTO EMP VALUES (1004, '최지은', '대리', 1003, TO_DATE('2020-05-20','YYYY-MM-DD'), 4100, NULL, 'choi.je@daehan.com',  20);
INSERT INTO EMP VALUES (1005, '정우성', '사원', 1004, TO_DATE('2022-11-01','YYYY-MM-DD'), 3200,  200, 'jung.ws@daehan.com',  20);
INSERT INTO EMP VALUES (1006, '한가인', '부장', 1001, TO_DATE('2017-02-28','YYYY-MM-DD'), 6200,  400, 'han.gi@daehan.com',   30);
INSERT INTO EMP VALUES (1007, '송강호', '과장', 1006, TO_DATE('2019-09-12','YYYY-MM-DD'), 5000, 1000, 'song.kh@daehan.com',  30);
INSERT INTO EMP VALUES (1008, '임수정', '사원', 1007, TO_DATE('2021-03-25','YYYY-MM-DD'), 3100, NULL, 'lim.sj@daehan.com',   30);
INSERT INTO EMP VALUES (1009, '오달수', '사원', 1007, TO_DATE('2023-06-05','YYYY-MM-DD'), 2900,  100, 'oh.ds@daehan.com',    30);
INSERT INTO EMP VALUES (1010, '유재석', '과장', 1001, TO_DATE('2018-12-03','YYYY-MM-DD'), 5500, NULL, 'yoo.js@daehan.com',   10);
INSERT INTO EMP VALUES (1011, '강호동', '사원', 1010, TO_DATE('2022-01-17','YYYY-MM-DD'), 3000,  250, 'kang.hd@daehan.com',  10);
INSERT INTO EMP VALUES (1012, '신동엽', '인턴', NULL, TO_DATE('2024-07-01','YYYY-MM-DD'), 2400, NULL, 'shin.dy@daehan.com', NULL);  -- 아직 부서 배정 전 (외부조인 실습용)

--------------------------------------------------------------------------------
-- 3. PRODUCT (상품)
--------------------------------------------------------------------------------
CREATE TABLE PRODUCT (
    PRODUCT_ID    NUMBER(4)     CONSTRAINT PK_PRODUCT PRIMARY KEY,  -- 상품번호
    PRODUCT_NAME  VARCHAR2(30)  NOT NULL,                           -- 상품명
    CATEGORY      VARCHAR2(20),                                     -- 분류
    PRICE         NUMBER(9)     NOT NULL                            -- 가격(원)
);

INSERT INTO PRODUCT VALUES (2001, '노트북',  '전자', 1500000);
INSERT INTO PRODUCT VALUES (2002, '모니터',  '전자',  350000);
INSERT INTO PRODUCT VALUES (2003, '키보드',  '전자',   80000);
INSERT INTO PRODUCT VALUES (2004, '의자',    '가구',  220000);
INSERT INTO PRODUCT VALUES (2005, '책상',    '가구',  450000);
INSERT INTO PRODUCT VALUES (2006, '마우스',  '전자',   30000);   -- 한 번도 안 팔린 상품 (외부조인 실습용)

--------------------------------------------------------------------------------
-- 4. ORDERS (주문)  * ORDER 는 오라클 예약어라 테이블명으로 못 쓴다. 그래서 ORDERS.
--------------------------------------------------------------------------------
CREATE TABLE ORDERS (
    ORDER_ID       NUMBER(6)    CONSTRAINT PK_ORDERS PRIMARY KEY,  -- 주문번호
    PRODUCT_ID     NUMBER(4)    NOT NULL,                          -- 주문한 상품번호
    EMP_ID         NUMBER(4),                                      -- 담당(판매) 사원번호
    CUSTOMER_NAME  VARCHAR2(20) NOT NULL,                          -- 고객이름
    QUANTITY       NUMBER(4)    NOT NULL,                          -- 수량
    ORDER_DATE     DATE,                                           -- 주문일
    CONSTRAINT FK_ORDERS_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID),
    CONSTRAINT FK_ORDERS_EMP     FOREIGN KEY (EMP_ID)     REFERENCES EMP(EMP_ID)
);

INSERT INTO ORDERS VALUES (5001, 2001, 1008, '나민호', 1, TO_DATE('2024-01-15','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5002, 2002, 1008, '고윤아', 2, TO_DATE('2024-02-03','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5003, 2003, 1009, '나민호', 3, TO_DATE('2024-02-20','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5004, 2001, 1007, '장서준', 2, TO_DATE('2024-03-11','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5005, 2005, 1008, '고윤아', 1, TO_DATE('2024-04-07','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5006, 2004, 1009, '문세윤', 4, TO_DATE('2024-05-19','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5007, 2002, 1007, '나민호', 1, TO_DATE('2024-06-25','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5008, 2003, 1008, '장서준', 5, TO_DATE('2024-07-08','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5009, 2001, 1009, '고윤아', 1, TO_DATE('2024-08-30','YYYY-MM-DD'));
INSERT INTO ORDERS VALUES (5010, 2005, 1007, '문세윤', 2, TO_DATE('2024-09-14','YYYY-MM-DD'));

-- 저장 확정 (트랜잭션은 ch3, ch12 에서 배운다)
COMMIT;

--------------------------------------------------------------------------------
-- 확인용 조회
--------------------------------------------------------------------------------
SELECT * FROM DEPT;
SELECT * FROM EMP;
SELECT * FROM PRODUCT;
SELECT * FROM ORDERS;
