package com.ioc.step4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ★ @Configuration = Step3의 AppAssembler를 Spring이 대신 해주는 것
 *
 * Step3의 AppAssembler와 동일한 역할을 Spring 어노테이션으로 표현.
 */
@Configuration
public class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();       // 여기만 바꾸면 전체 교체
        // return new JpaMemberRepository();        // ← 이렇게!
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());  // 생성자 주입
    }

    @Bean
    public MemberController memberController() {
        return new MemberController(memberService());  // 생성자 주입
    }
}

