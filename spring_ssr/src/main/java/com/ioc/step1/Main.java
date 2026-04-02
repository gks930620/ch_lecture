package com.ioc.step1;

/**
 * [STEP 1] 직접 객체 생성 방식 - IoC 적용 전
 *
 * 문제점:
 * - Service가 직접 Repository를 생성 → 강한 결합(의존)
 * - Repository 구현체를 바꾸려면 Service 코드를 수정해야 함
 * - 테스트 시 Mock으로 교체 불가능
 */
public class Main {

    public static void main(String[] args) {
        MemberService service = new MemberService();
        System.out.println("[STEP1] 직접 생성: " + service.getMember(1L));

        /*
         * 문제 상황 예시:
         * - MemberRepository를 JpaMemberRepository로 바꾸고 싶다면?
         * - MemberService 내부 코드를 직접 수정해야 한다 → OCP 위반
         */
    }
}

