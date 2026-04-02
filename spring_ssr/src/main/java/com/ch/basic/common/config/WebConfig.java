package com.ch.basic.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 설정 클래스
 *
 * WebMvcConfigurer: Spring MVC의 기본 설정을 커스터마이징할 수 있는 인터페이스
 *   - Interceptor 등록, 정적 리소스 매핑, CORS 설정 등을 여기서 함
 *
 * @Configuration: Spring 설정 클래스 선언
 * @RequiredArgsConstructor: final 필드에 대한 생성자 자동 생성 → 의존성 주입
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // LoginCheckInterceptor를 생성자 주입으로 받음 (@Component로 등록되어 있으므로 주입 가능)
    private final LoginCheckInterceptor loginCheckInterceptor;


    /**
     * 정적 리소스(이미지 등) 서빙 설정 — 정석적인 방식
     *
     * /uploads/** 요청 → 로컬 파일시스템의 uploads/ 폴더에서 파일을 찾아 응답
     * 예: <img src="/uploads/uuid.png"> → file:///C:/.../uploads/uuid.png 반환
     *
     * ※ 현재 이 프로젝트에서는 사용하지 않음 (주석 처리)
     *    파일 관련 처리를 FileController에서 일관되게 관리하기 위해
     *    FileController.serveImage() (GET /uploads/{filename})에서 대신 처리 중
     *
     * ※ 정석은 여기서 하는 것이 맞음 (Spring이 직접 서빙 → 성능 더 좋음)
     *    FileController 방식은 매 요청마다 Controller → Service 거치므로 약간의 오버헤드 있음
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // /uploads/** URL 요청을 file:./uploads/ 디렉토리에서 찾아 응답
//        registry.addResourceHandler("/uploads/**")
//                .addResourceLocations("file:./uploads/");
//    }

    /**
     * Interceptor 등록 및 적용 경로 설정
     *
     * addPathPatterns(): 이 경로에 접근할 때 Interceptor 실행 (로그인 체크 대상)
     * excludePathPatterns(): 이 경로는 Interceptor 적용 제외 (비로그인도 접근 가능)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns(
                        "/community/write",          // 글 작성 페이지 → 로그인 필요
                        "/community/*/edit",         // 글 수정 페이지 → 로그인 필요 (* = 글 ID)
                        "/community/*/delete",       // 글 삭제 → 로그인 필요
                        "/mypage"                    // 마이페이지 → 로그인 필요
                        // ※ 댓글 API(/api/**)는 여기서 등록하지 않음
                        //    → CommentApiController에서 자체적으로 세션 체크 후 401 JSON 응답
                        //    API가 많아지면 ApiLoginCheckInterceptor를 별도로 만들어서
                        //    실패 시 401 JSON 응답하는 방식으로 분리하는 것이 정석
                )
                .excludePathPatterns(
                        "/login", "/signup",         // 로그인/회원가입 → 비로그인 접근 가능 (당연)
                        "/", "/community",           // 홈, 목록 → 비로그인도 조회 가능
                        "/community/*",              // 상세 조회 → 비로그인도 조회 가능
                        "/uploads/**",               // 업로드된 파일 (이미지 등) → 누구나 접근
                        "/css/**", "/js/**"          // 정적 리소스 (CSS, JS) → 누구나 접근
                );
    }

}
