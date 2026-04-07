INSERT INTO member (
                    mem_id,mem_pass,mem_name,mem_bir
                   ,mem_zip,mem_add1,mem_add2,mem_hp
                   ,mem_mail,mem_job,mem_hobby
                   ,mem_del_yn
) VALUES (
                 'a00'||seq_member.nextval, '1004','한창희',sysdate
         ,'34502','우리집 아파트','201호','010-8000-8000'
         ,'gksbir@naver.com','JB01','HB01'
         ,'N'
         );


-- 적당히 몇번 실행한 후

update member set mem_name='버니즈' where MOD(substr(mem_id,3),3)=0;
update member set mem_name='다이브' where MOD(substr(mem_id,3),3)=1;
update member set mem_name='마이'where MOD(substr(mem_id,3),3)=2;





