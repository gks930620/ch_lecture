package com.ioc.step2;

/**
 * 인터페이스 (추상화)
 * - Service는 이 인터페이스에만 의존 → 구현체를 몰라도 됨
 */
public interface MemberRepository {
    String findById(Long id);
}

