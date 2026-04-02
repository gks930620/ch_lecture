package com.ioc.step2;

/**
 * 구현체 B - DB 저장소 (가상)
 */
public class JpaMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[JPA] 회원_" + id;
    }
}

