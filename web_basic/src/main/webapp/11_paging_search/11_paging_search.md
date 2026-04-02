# 11 페이징 및 검색 — 상세 강의노트

목차
- 페이징의 목적과 기본 개념
- 파라미터 설계(page, size, q 등)
- SQL에서의 offset/limit과 성능 고려
- 검색 연동(WHERE 절 동적 구성)
- UI/UX: 페이지 네비게이션 구성 방법
- 실습: DAO에 offset/limit 적용, JSP에서 네비게이션 표시

1) 페이징의 목적
- 대량 데이터 한 번에 모두 로드하는 것을 방지하고, 사용자에게 나누어 보여줘 응답성과 UX를 개선.

2) 파라미터 설계
- `page` (int): 현재 페이지(1 기반 권장)
- `size` (int): 페이지당 항목 수
- `q` (string): 검색어
- offset 계산: `offset = (page - 1) * size`

3) SQL 예제
- 기본 페이징(MySQL)
  ```sql
  SELECT * FROM board
  WHERE title LIKE CONCAT('%', #{q}, '%')
  ORDER BY id DESC
  LIMIT #{offset}, #{limit}
  ```
- 성능 팁
  - OFFSET이 큰 경우 성능 저하가 있으므로 keyset pagination 고려(예: WHERE id < last_id LIMIT size)
  - 인덱스 설계: 검색 대상 컬럼에 대한 적절한 인덱스 필요(복합 인덱스 고려)

4) 검색과 페이징 연동
- 검색어가 있는 경우 전체 카운트(total)를 재계산해야 정확한 페이지 수를 산출.
- MyBatis 매퍼에서 `count` 쿼리와 `selectList` 쿼리를 분리해 구현.

5) UI/UX 권장사항
- 페이지 네비게이션: 이전/다음, 숫자 페이지, 첫/끝 이동 버튼 제공
- 현재 페이지 강조, 비활성 버튼 처리
- 검색어 보존: 검색 후에도 검색어 파라미터를 페이징 링크에 포함

6) 실습 지침
- `11_paging_demo.jsp`에서 `page`, `size`, `q` 파라미터를 조작해 결과 변화를 확인.
- `BoardController` 및 DAO(selectList)에서 offset/limit을 적용해 페이징 목록을 반환하도록 구현.
- 추가: 전체 카운트 조회 후 총 페이지 수를 계산해 페이지 네비게이션 표시.

이 문서는 페이징과 검색을 실제 서비스에 적용할 때 필요한 개념, SQL, UI 고려사항을 포괄적으로 정리했습니다.
