package com.ioc.step4;

/**
 * 구현체 — AppConfig(@Bean)에서 이 구현체를 선택하여 등록
 */
public class MemoryMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[Memory] 회원_" + id;
    }
}
