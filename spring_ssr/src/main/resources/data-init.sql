-- ※ 이 파일은 application.yml의 data-locations에 등록되어 있지 않아 자동 실행되지 않음
-- 수동으로 DB를 초기화하고 싶을 때 직접 실행하세요
-- 서버 시작 시 FK 체크 비활성화 (이전 스키마 잔재 제거용)
SET FOREIGN_KEY_CHECKS = 0;

-- 혹시 남아있는 테이블 강제 삭제
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS community;
DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;

