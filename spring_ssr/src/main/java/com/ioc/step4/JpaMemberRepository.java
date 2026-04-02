package com.ioc.step4;

public class JpaMemberRepository implements MemberRepository {

    @Override
    public String findById(Long id) {
        return "[JPA] 회원_" + id;
    }
}

