package com.aop.step4;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * ★ Spring AOP - @Aspect로 선언적 AOP 적용
 *
 * Step2: 메서드마다 프록시 코드를 작성 (수동)
 * Step3: InvocationHandler 하나로 전체 적용 (Dynamic Proxy)
 * Step4: @Aspect + Pointcut 으로 선언적 적용 (Spring AOP)
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  Step3                          │  Step4 (Spring AOP)       │
 * ├─────────────────────────────────┼───────────────────────────┤
 * │  InvocationHandler              │  @Aspect 클래스           │
 * │  invoke(proxy, method, args)    │  @Around 메서드           │
 * │  method.invoke(target, args)    │  joinPoint.proceed()      │
 * │  Proxy.newProxyInstance(...)    │  Spring이 자동 생성       │
 * │  모든 메서드에 무조건 적용      │  Pointcut으로 선택 가능   │
 * └─────────────────────────────────┴───────────────────────────┘
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Pointcut: com.aop.step4 패키지의 *ServiceImpl 클래스의 모든 메서드에 적용
     *
     * execution(접근제어자 반환타입 패키지.클래스.메서드(파라미터))
     * execution(* com.aop.step4.*ServiceImpl.*(..))
     *            ↑                     ↑         ↑ ↑
     *         모든 반환타입      ServiceImpl로 끝나는 클래스  모든 메서드  모든 파라미터
     */
    @Around("execution(* com.aop.step4.*ServiceImpl.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        // ─── 부가 로직: 실행 전 (= Step3의 method.invoke() 전 코드) ───
        long start = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("[SPRING AOP LOG] " + className + "." + methodName + "() 시작");
        System.out.println("[SPRING AOP LOG] 파라미터: " + java.util.Arrays.toString(joinPoint.getArgs()));

        // ★ 핵심 로직 실행 (= Step3의 method.invoke(target, args))
        Object result = joinPoint.proceed();

        // ─── 부가 로직: 실행 후 ───
        long end = System.currentTimeMillis();
        System.out.println("[SPRING AOP LOG] " + className + "." + methodName + "() 종료 - 실행시간: " + (end - start) + "ms");
        System.out.println("[SPRING AOP LOG] 반환값: " + result);

        return result;
    }
}

