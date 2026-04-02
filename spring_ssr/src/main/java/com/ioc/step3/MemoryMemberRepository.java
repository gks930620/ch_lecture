package com.ioc.step3;

/**
 * 구현체 A — 메모리 저장소 (Assembler에서 선택)
 */
public class MemoryMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[Memory] 회원_" + id;
    }
}
