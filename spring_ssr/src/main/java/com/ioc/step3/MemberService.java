package com.ioc.step3;

/**
 * Service — 인터페이스(MemberRepository)에만 의존, 구현체를 모름
 * 생성자 주입으로 외부(Assembler)에서 구현체를 받음
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
