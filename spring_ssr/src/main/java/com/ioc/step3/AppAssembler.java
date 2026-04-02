package com.ioc.step3;

/**
 * ★ Assembler (조립기) - Spring 컨테이너의 원형
 *
 * 객체 생성 + 의존성 조립을 전담하는 클래스.
 * 구현체를 바꾸려면 이 클래스 한 곳만 수정하면 됨.
 */
public class AppAssembler {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MemberController memberController;

    public AppAssembler() {
        // ① 객체 생성
        this.memberRepository = new MemoryMemberRepository();   // 여기만 바꾸면 전체 교체
        // this.memberRepository = new JpaMemberRepository();   // ← 이렇게!

        // ② 의존성 조립 (DI)
        this.memberService = new MemberService(memberRepository);
        this.memberController = new MemberController(memberService);
    }

    // 외부에서 가져다 쓸 수 있게 제공 (= Bean 조회)
    public MemberRepository getMemberRepository() { return memberRepository; }
    public MemberService getMemberService() { return memberService; }
    public MemberController getMemberController() { return memberController; }
}

