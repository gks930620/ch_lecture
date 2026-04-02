package com.ch.basic.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 공공데이터 API 프록시 컨트롤러
 *
 * 왜 프록시가 필요한가?
 * - 브라우저(JS)에서 직접 공공데이터 API를 호출하면 CORS 에러 발생
 * - 서버(Spring)에서 대신 호출(프록시)하면 CORS 문제 없음
 *   (서버 → 서버 통신은 브라우저의 동일 출처 정책 적용 대상이 아님)
 *
 * 흐름: 브라우저 JS → 우리 Spring 서버(프록시) → 공공데이터 API → 응답 → JS
 *
 * ──────────────────────────────────────────────
 * RestTemplate vs RestClient (Spring Boot 3.2+ / Spring Framework 6.1+)
 * ──────────────────────────────────────────────
 * RestTemplate (구버전)
 *   - Spring 3.0(2009)부터 존재한 동기 HTTP 클라이언트
 *   - Spring 6.1부터 "유지보수 모드" (maintenance mode) — 새 기능 추가 없음
 *   - 사용법: restTemplate.getForEntity(url, String.class)
 *
 * RestClient (신버전, 권장 ★)
 *   - Spring 6.1 / Boot 3.2 에서 새로 도입된 동기 HTTP 클라이언트
 *   - Fluent API (메서드 체이닝) 방식으로 가독성 좋음
 *   - 내부적으로 RestTemplate과 같은 인프라 사용 (호환성 문제 없음)
 *   - 사용법: restClient.get().uri(uri).retrieve().body(String.class)
 *
 * ※ 비동기가 필요하면 WebClient (spring-boot-starter-webflux 필요)
 * ※ 추가 의존성 불필요 — spring-boot-starter-web에 포함되어 있음
 * ──────────────────────────────────────────────
 */
@Slf4j
@RestController
@RequestMapping("/api/proxy")
public class ApiProxyController {

    // RestClient: Spring 6.1+ 에서 권장하는 동기 HTTP 클라이언트
    // RestClient.create() — 기본 설정으로 생성 (커스텀이 필요하면 .builder() 사용)
    private final RestClient restClient = RestClient.create();

    // 인증키: .env 파일 → application.yml(${API_SERVICE_KEY}) → @Value 주입
    // .env 파일은 .gitignore에 추가하여 소스코드에 키가 노출되지 않도록 관리
    // spring-dotenv 라이브러리가 .env 파일을 자동으로 읽어 Spring 프로퍼티로 등록
    @Value("${api.service-key}")
    private String serviceKey;

    /**
     * API 1: 시군구별 관광기후지수 조회
     * GET /api/proxy/city-climate
     *
     * 미리보기 URL:
     * getCityTourClmIdx1?serviceKey=...&CURRENT_DATE=2018123110&DAY=3&CITY_AREA_ID=5013000000
     */
    @GetMapping("/city-climate")
    public String getCityClimate(
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "JSON") String dataType,
            @RequestParam String CURRENT_DATE,
            @RequestParam(defaultValue = "3") String DAY,
            @RequestParam String CITY_AREA_ID
    ) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://apis.data.go.kr/1360000/TourStnInfoService1/getCityTourClmIdx1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("dataType", dataType)
                .queryParam("CURRENT_DATE", CURRENT_DATE)
                .queryParam("DAY", DAY)
                .queryParam("CITY_AREA_ID", CITY_AREA_ID)
                .build(true)
                .toUri();

        log.info("[프록시] 시군구별 관광기후지수 호출: {}", uri);

        // RestClient Fluent API:
        //   .get()           → HTTP GET 방식 지정
        //   .uri(uri)        → URI 객체를 넘김 (이중 인코딩 방지)
        //   .retrieve()      → 요청 실행 & 응답 수신
        //   .body(String.class) → 응답 body를 String으로 변환하여 반환
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }

    /**
     * API 2: 동네예보 조회
     * GET /api/proxy/tour-forecast
     *
     * 미리보기 URL:
     * getTourStnVilageFcst1?serviceKey=...&CURRENT_DATE=2019122010&HOUR=24&COURSE_ID=1
     */
    @GetMapping("/tour-forecast")
    public String getTourForecast(
            @RequestParam(defaultValue = "1") String pageNo,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "JSON") String dataType,
            @RequestParam String CURRENT_DATE,
            @RequestParam(defaultValue = "24") String HOUR,
            @RequestParam String COURSE_ID
    ) {
        // UriComponentsBuilder: URL + 쿼리파라미터를 안전하게 조립하는 빌더
        // .build(true): 이미 인코딩된 값(ServiceKey의 %2F 등)을 다시 인코딩하지 않음
        // .toUri(): URI 객체로 변환 → RestClient도 URI 객체를 넘기면 재인코딩 안 함
        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://apis.data.go.kr/1360000/TourStnInfoService1/getTourStnVilageFcst1")
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("dataType", dataType)
                .queryParam("CURRENT_DATE", CURRENT_DATE)
                .queryParam("HOUR", HOUR)
                .queryParam("COURSE_ID", COURSE_ID)
                .build(true)
                .toUri();

        log.info("[프록시] 동네예보 호출: {}", uri);

        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);
    }
}
