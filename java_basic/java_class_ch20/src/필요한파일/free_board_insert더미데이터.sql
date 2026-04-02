INSERT INTO free_board (
                        bo_no,bo_title,bo_category,bo_writer
                       ,bo_pass,bo_content
                       ,bo_reg_date,bo_mod_date,bo_del_yn
) VALUES (
             SEQ_FREE_BOARD.nextval, '제목1', 'BC01','한창희'
         ,'1004','내용은 길게'
         ,sysdate,null,'N'
         );

-- 적당히 몇번 실행 한후
update free_board set bo_title='뉴진스 짱' where MOD(BO_NO,3)=0;
update free_board set bo_title='아이브 짱' where MOD(BO_NO,3)=1;
update free_board set bo_title='에스파 짱' where MOD(BO_NO,3)=2;

