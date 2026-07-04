package com.aop.step3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ★ InvocationHandler — 하나로 모든 Service에 적용!
 *
 * 프록시 객체의 어떤 메서드가 호출되든 전부 invoke() 하나로 들어온다.
 * → Step2처럼 메서드마다 프록시 코드를 작성할 필요가 없음
 * → OrderService든 PaymentService든 target만 바꿔서 재사용 가능
 *
 * Spring AOP와의 대응 관계:
 *   invoke(proxy, method, args)  =  @Around 메서드
 *   method.invoke(target, args)  =  joinPoint.proceed()
 */
public class LoggingHandler implements InvocationHandler {

    private final Object target;   // 실제 객체 (어떤 타입이든 가능)

    public LoggingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // ─── 부가 로직: 실행 전 ───
        long start = System.currentTimeMillis();
        System.out.println("[DYNAMIC PROXY LOG] " + method.getName() + "() 시작");

        // ★ 핵심 로직 실행 (리플렉션으로 실제 객체의 메서드 호출)
        Object result = method.invoke(target, args);

        // ─── 부가 로직: 실행 후 ───
        long end = System.currentTimeMillis();
        System.out.println("[DYNAMIC PROXY LOG] " + method.getName() + "() 종료 - 실행시간: " + (end - start) + "ms");
        return result;
    }
}
