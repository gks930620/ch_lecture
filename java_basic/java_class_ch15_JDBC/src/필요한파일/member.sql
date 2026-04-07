CREATE SEQUENCE seq_member;


CREATE TABLE member (
   mem_id     varchar2(100) primary key,
   mem_pass   varchar2(100),
   mem_name   varchar2(100),
   mem_bir date,
   mem_zip   varchar2(100),
   mem_add1   varchar2(100),
   mem_add2   varchar2(100),
   mem_hp   varchar2(100),
   mem_mail   varchar2(100),
   mem_job   char(4),
   mem_hobby   char(4),
   mem_del_yn   varchar2(100)
);

COMMENT ON COLUMN member.mem_id IS '회원아이디';
COMMENT ON COLUMN member.mem_pass IS '회원비밀번호';
COMMENT ON COLUMN member.mem_name IS '회원이름';
COMMENT ON COLUMN member.mem_bir IS '회원생년월일';
COMMENT ON COLUMN member.mem_zip IS '회원우편번호';
COMMENT ON COLUMN member.mem_add1 IS '회원주소';
COMMENT ON COLUMN member.mem_add2 IS '회원상세주소';
COMMENT ON COLUMN member.mem_hp IS '회원전화번호';
COMMENT ON COLUMN member.mem_mail IS '회원이메일';
COMMENT ON COLUMN member.mem_job IS '회원직업';
COMMENT ON COLUMN member.mem_hobby IS '회원취미';
COMMENT ON COLUMN member.mem_del_yn IS '회원삭제여부';





