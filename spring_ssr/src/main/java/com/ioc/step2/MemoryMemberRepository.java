package com.ioc.step2;

/**
 * 구현체 A - 메모리 저장소
 */
public class MemoryMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[Memory] 회원_" + id;
    }
}

