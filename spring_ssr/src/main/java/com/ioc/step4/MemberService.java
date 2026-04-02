package com.ioc.step4;

/**
 * Service — 인터페이스에만 의존, Spring이 구현체를 주입
 */
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String getMember(Long id) {
        return memberRepository.findById(id);
    }
}
