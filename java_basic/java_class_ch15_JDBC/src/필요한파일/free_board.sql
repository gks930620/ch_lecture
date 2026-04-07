

CREATE SEQUENCE seq_free_board;

CREATE TABLE free_board (
   bo_no     NUMBER NOT NULL, 
   bo_title  VARCHAR2(250) NOT NULL,
   bo_category  CHAR(4) , 
   bo_writer VARCHAR2(100) NOT NULL,
   bo_pass   varchar2(60)  not null, 
   bo_content CLOB,
   bo_reg_date DATE DEFAULT SYSDATE,
   bo_mod_date DATE ,
   bo_del_yn  CHAR(1) DEFAULT 'N',
   CONSTRAINT pk_free_board PRIMARY KEY(bo_no)
);

COMMENT ON TABLE free_board  IS '자유 게시판';
COMMENT ON COLUMN free_board.bo_no IS '글 번호';
COMMENT ON COLUMN free_board.bo_title IS '글 제목';
COMMENT ON COLUMN free_board.bo_category IS '글 분류 코드';
COMMENT ON COLUMN free_board.bo_writer IS '작성자명';
COMMENT ON COLUMN free_board.bo_pass IS '비밀번호';
COMMENT ON COLUMN free_board.bo_content IS '글 내용';
COMMENT ON COLUMN free_board.bo_reg_date IS '등록 일자';
COMMENT ON COLUMN free_board.bo_mod_date IS '수정 일자';
COMMENT ON COLUMN free_board.bo_del_yn IS '삭제 여부';



