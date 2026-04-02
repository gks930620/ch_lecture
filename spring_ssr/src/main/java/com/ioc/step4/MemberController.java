package com.ioc.step4;

/**
 * Controller — Spring 컨테이너(@Bean)가 생성 + 주입해줌
 * Step3의 Assembler 방식을 Spring이 대신 해준다
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
