package com.ioc.step2;

/**
 * Service - 인터페이스에만 의존 (구현체를 모름)
 *
 * ✅ 생성자 주입 - 외부에서 구현체를 넣어줌
 */
public class MemberService {

    private final MemberRepository memberRepository;   // 인터페이스 타입

    // ✅ 생성자 주입 - 외부에서 구현체를 넣어줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String getMember(Long id) {
        return memberRepository.findById(id);
    }
}

