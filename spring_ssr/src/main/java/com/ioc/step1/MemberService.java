package com.ioc.step1;

/**
 * Service (비즈니스 계층)
 *
 * ❌ 직접 new로 Repository를 생성 → 강한 결합
 * - Repository 구현체를 바꾸려면 이 코드를 수정해야 함
 * - 테스트 시 Mock으로 교체 불가능
 */
public class MemberService {

    // ❌ 직접 생성 → 강한 결합
    private MemberRepository memberRepository = new MemberRepository();

    public String getMember(Long id) {
        return memberRepository.findById(id);
    }
}

