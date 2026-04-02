package com.ioc.step2;

/**
 * [STEP 2] 인터페이스 도입 + 생성자 주입 (DI)
 *
 * 개선점:
 * - Repository를 인터페이스로 분리 → 느슨한 결합
 * - Service는 "어떤 구현체"인지 모름 → 인터페이스에만 의존
 * - 생성자로 외부에서 주입받음 → 제어가 역전됨 (IoC의 시작)
 *
 * 남은 문제:
 * - main()에서 직접 new로 조립 → 누군가는 여전히 구현체를 알아야 함
 */
public class Main {

    public static void main(String[] args) {
        // 구현체 선택은 여기서 결정 (Service는 모름)
        MemberRepository repository = new MemoryMemberRepository();
        MemberService service = new MemberService(repository);
        System.out.println("[STEP2] 생성자 주입(Memory): " + service.getMember(1L));

        // 구현체를 바꿔도 Service 코드 변경 없음!
        MemberRepository jpaRepo = new JpaMemberRepository();
        MemberService service2 = new MemberService(jpaRepo);
        System.out.println("[STEP2] 생성자 주입(JPA):    " + service2.getMember(1L));

        /*
         * 개선됨:
         * - Service는 MemberRepository 인터페이스에만 의존
         * - 구현체 변경 시 Service 코드 수정 불필요 → OCP 충족
         *
         * 남은 문제:
         * - main()에서 직접 new 하고 조립 → 이 역할을 분리할 수 없을까?
         * - → Step3: Assembler 등장
         */
    }
}

