package com.ioc.step1;

/**
 * Repository (데이터 접근 계층)
 */
public class MemberRepository {

    public String findById(Long id) {
        return "회원_" + id;   // DB 대신 단순 문자열 반환
    }
}

