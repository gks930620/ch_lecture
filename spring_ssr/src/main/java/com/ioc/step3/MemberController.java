package com.ioc.step3;

/**
 * Controller — Assembler가 생성한 Service를 주입받아 사용
 * Controller도 직접 Service를 생성하지 않음 → IoC (제어의 역전)
 */
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public String getMemberInfo(Long id) {
        return "Controller 응답: " + memberService.getMember(id);
    }
}
