package com.ioc.step3;

/**
 * 구현체 B — DB 저장소 (가상, Assembler에서 교체 가능)
 */
public class JpaMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[JPA] 회원_" + id;
    }
}
