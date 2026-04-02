package com.ioc.step3;

/**
 * [STEP 3] Assembler (조립기) - IoC 컨테이너의 원형
 *
 * 개선점:
 * - 객체 생성 + 의존성 조립을 전담하는 AppAssembler 등장
 * - Service, Controller 모두 "누가 만들어주는지" 모름
 * - 이 Assembler가 바로 Spring 컨테이너(ApplicationContext)의 원형
 */
public class Main {

    public static void main(String[] args) {
        // Assembler가 모든 객체를 생성하고 조립
        AppAssembler assembler = new AppAssembler();

        // 사용하는 쪽은 그냥 가져다 쓰기만 함
        MemberController controller = assembler.getMemberController();
        System.out.println("[STEP3] " + controller.getMemberInfo(1L));

        /*
         * ★ 핵심 정리
         *
         * 1. Service, Controller는 스스로 의존 객체를 생성하지 않음
         *    → 제어가 역전됨 (IoC)
         *
         * 2. Assembler가 생성 + 주입을 전담
         *    → Spring의 ApplicationContext와 같은 역할
         *
         * 3. 구현체를 바꾸려면 Assembler 한 곳만 수정
         *    → OCP (개방-폐쇄 원칙) 충족
         *
         * ┌─────────────────────────────────────────┐
         * │  Assembler (= Spring Container)         │
         * │  ┌───────────────────────────────────┐  │
         * │  │ new MemoryMemberRepository()      │  │
         * │  │         ↓ 주입                     │  │
         * │  │ new MemberService(repository)     │  │
         * │  │         ↓ 주입                     │  │
         * │  │ new MemberController(service)     │  │
         * │  └───────────────────────────────────┘  │
         * └─────────────────────────────────────────┘
         *
         * → Step4에서 이걸 Spring이 대신 해준다!
         */
    }
}

