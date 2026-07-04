-- 파일 테이블 샘플 데이터
-- ※ 실제 물리 파일(sample.jpeg)이 uploads/ 폴더에 없으므로 주석 처리
-- ※ original_file_name = 사용자가 올린 원본 이름, stored_file_name = 서버에 저장되는 UUID 기반 이름
-- INSERT INTO files (created_at,file_size,ref_id,updated_at,content_type,file_path,original_file_name,stored_file_name,ref_type,file_usage) VALUES
--  ('2026-01-08 18:06:27.000000',1024,1,'2026-01-08 18:06:27.000000','image/jpeg','/uploads/550e8400-e29b-41d4-a716-446655440000.jpeg','sample.jpeg','550e8400-e29b-41d4-a716-446655440000.jpeg','COMMUNITY','THUMBNAIL');
SELECT 1;

