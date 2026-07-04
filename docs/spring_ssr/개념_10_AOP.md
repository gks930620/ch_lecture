# AOP (Aspect Oriented Programming)

> 📁 실습 코드: `com.aop.step1` ~ `com.aop.step4`  
> IoC/DI처럼 순수 자바 → Spring AOP까지 단계적으로 학습

---

## 0. 학습 흐름 (Step 1 → 4)

| Step | 패키지 | 핵심 | 실행 |
|------|--------|------|------|
| **Step 1** | `com.aop.step1` | AOP 없이 - 부가 로직이 핵심 로직에 직접 섞임 | `step1.Main` 실행 |
| **Step 2** | `com.aop.step2` | 프록시 패턴 - 수동으로 부가 로직 분리 | `step2.Main` 실행 |
| **Step 3** | `com.aop.step3` | JDK Dynamic Proxy - 리플렉션으로 자동 프록시 | `step3.Main` 실행 |
| **Step 4** | `com.aop.step4` | Spring AOP - @Aspect + Pointcut 선언적 적용 | `step4.Main` 실행 |

```
Step1: 모든 메서드에 로깅 코드 중복   → "이거 너무 불편한데?"
Step2: 프록시 패턴으로 분리           → "분리했지만 프록시 클래스를 직접 작성해야 하네..."
Step3: Dynamic Proxy로 자동화        → "하나의 Handler로 해결! 근데 Pointcut이 불편..."
Step4: Spring AOP로 완전 자동화      → "선언만 하면 Spring이 다 해줌!"
```

---

## 1. AOP란?

**핵심 로직(비즈니스)** 과 **부가 로직(공통 관심사)** 을 분리하는 프로그래밍 방식.

```
부가 로직 예시:
- 로깅 (메서드 실행 시간 측정)
- 트랜잭션 (@Transactional이 내부적으로 AOP)
- 인증/권한 체크
- 예외 처리
```

### 문제 상황

```java
// ❌ AOP 없이 - 모든 서비스에 로깅 코드가 중복
public class CommunityService {
    public CommunityDTO getCommunityDetail(Long id) {
        long start = System.currentTimeMillis();
        log.info("getCommunityDetail 시작");
        // ... 핵심 로직
        log.info("getCommunityDetail 종료: {}ms", System.currentTimeMillis() - start);
    }
}

public class FileService {
    public FileEntity getFileById(Long id) {
        long start = System.currentTimeMillis();
        log.info("getFileById 시작");
        // ... 핵심 로직
        log.info("getFileById 종료: {}ms", System.currentTimeMillis() - start);
    }
}
```

### AOP로 해결

```java
// ✅ AOP - 로깅 코드를 한 곳에만 작성
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Service 계층의 모든 메서드에 적용
    @Around("execution(* com.ch.basic..*Service.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        log.info("{} 시작", methodName);
        Object result = joinPoint.proceed(); // 실제 메서드 실행
        log.info("{} 종료: {}ms", methodName, System.currentTimeMillis() - start);

        return result;
    }
}
```

---

## 2. 핵심 용어

| 용어 | 설명 | 예시 |
|------|------|------|
| **Aspect** | 부가 로직을 모아둔 클래스 | `LoggingAspect` |
| **Advice** | 실제 부가 로직 (언제 실행할지) | `@Before`, `@After`, `@Around` |
| **Pointcut** | 어디에 적용할지 표현식 | `execution(* *..*Service.*(..))` |
| **JoinPoint** | 적용 가능한 지점 | 메서드 실행 시점 |
| **Target** | 부가 로직이 적용되는 실제 객체 | `CommunityService` |
| **Proxy** | Target을 감싸는 AOP 객체 | Spring이 자동 생성 |
| **Weaving** | Aspect를 Target에 적용(결합)하는 과정 | Spring AOP는 **런타임에 프록시를 만들어 위빙** |

---

## 3. Advice 종류

```java
@Aspect
@Component
public class ExampleAspect {

    // 메서드 실행 전
    @Before("execution(* com.ch.basic..*Service.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info("메서드 실행 전: {}", joinPoint.getSignature().getName());
    }

    // 메서드 실행 후 (예외 여부 상관없이)
    @After("execution(* com.ch.basic..*Service.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info("메서드 실행 후");
    }

    // 정상 반환 후
    @AfterReturning(pointcut = "execution(* com.ch.basic..*Service.*(..))",
                    returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("정상 반환: {}", result);
    }

    // 예외 발생 시
    @AfterThrowing(pointcut = "execution(* com.ch.basic..*Service.*(..))",
                   throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.error("예외 발생: {}", ex.getMessage());
    }

    // 전/후 모두 제어 (가장 많이 씀)
    @Around("execution(* com.ch.basic..*Service.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 실행 전
        Object result = joinPoint.proceed(); // 실제 메서드 실행
        // 실행 후
        return result;
    }
}
```

---

## 4. Pointcut 표현식

```
execution(접근제어자 반환타입 패키지.클래스.메서드(파라미터))

execution(* com.ch.basic..*Service.*(..))
           ↑           ↑            ↑  ↑
           모든 반환   ..(하위 패키지 포함)  모든 메서드, 모든 파라미터
```

| 패턴 | 설명 |
|------|------|
| `*` | 모든 값 (단, 패키지 구분자 `.`는 포함하지 않음) |
| `..` | 0개 이상의 패키지/파라미터 |
| `execution(* *..*Service.*(..))` | 이름이 Service로 끝나는 모든 클래스의 모든 메서드 |
| `@annotation(org.springframework.transaction.annotation.Transactional)` | @Transactional 붙은 메서드 (표현식 안에서는 import가 적용되지 않아 전체 패키지 경로 필요) |

> ⚠️ 주의: `execution(* *Service.*(..))` 처럼 쓰면 `*`가 패키지를 포함하지 않으므로
> **기본(default) 패키지**의 클래스만 매칭된다. 패키지에 상관없이 잡으려면 `*..*Service`로 써야 한다.

---

## 5. 실무 사용 예시

```java
// 실행 시간 측정
@Around("execution(* com.ch.basic..*Service.*(..))")
public Object logTime(ProceedingJoinPoint pjp) throws Throwable { ... }

// 로그인 체크 (Interceptor와 유사)
@Before("execution(* com.ch.basic..*.write(..))")
public void checkLogin(JoinPoint jp) { ... }

// 파라미터 로깅
@Before("execution(* com.ch.basic..*Controller.*(..))")
public void logRequest(JoinPoint jp) {
    log.info("요청: {} - 파라미터: {}", jp.getSignature(), jp.getArgs());
}
```

---

## 6. @Transactional도 AOP다

```
CommunityService.createCommunity() 호출
    ↓
실제로는 Spring이 만든 Proxy 객체가 먼저 받음
    ↓
Proxy: 트랜잭션 시작
    ↓
실제 CommunityService.createCommunity() 실행
    ↓
Proxy: 성공 → 커밋 / 예외 → 롤백
```

### ⚠️ Spring AOP 주의사항 (실무 필수)

Spring AOP는 **프록시 기반**이라 다음 한계가 있다.

1. **자기 호출(self-invocation)에는 적용 안 됨**  
   같은 클래스 안에서 `this.method()`로 호출하면 프록시를 거치지 않아
   AOP(특히 `@Transactional`)가 무시된다. → 자세한 예시는 `개념_06_Transaction.md` 4장 참고
2. **private / final 메서드에는 적용 안 됨**  
   프록시가 오버라이드할 수 없기 때문.
3. Bean이 아닌 객체(`new`로 직접 생성)에는 적용 안 됨 — 프록시는 Spring 컨테이너가 만들어주므로.

---

## 7. 실습 코드 상세 (Step 1 → 4)

### Step 1: AOP 없이 (`com.aop.step1`)

```
파일 구조:
  step1/
  ├── Main.java              ← 실행
  ├── OrderService.java      ← 핵심 로직 + 부가 로직 섞여있음
  └── OrderRepository.java   ← 데이터 접근
```

**핵심 포인트**: OrderService의 모든 메서드에 로깅/시간측정 코드가 중복됨

```java
// ❌ 모든 메서드에 같은 코드가 반복!
public String getOrder(Long id) {
    long start = System.currentTimeMillis();        // 중복
    System.out.println("[LOG] getOrder() 시작");     // 중복
    String result = orderRepository.findById(id);   // ← 핵심 로직은 이것뿐
    long end = System.currentTimeMillis();           // 중복
    System.out.println("[LOG] getOrder() 종료");     // 중복
    return result;
}
```

---

### Step 2: 프록시 패턴 (`com.aop.step2`)

```
파일 구조:
  step2/
  ├── Main.java                ← 실행
  ├── OrderService.java        ← 인터페이스 (프록시 패턴의 핵심)
  ├── OrderServiceImpl.java    ← 핵심 로직만 (부가 로직 없음)
  ├── OrderServiceProxy.java   ← ★ 부가 로직을 전담하는 프록시
  └── OrderRepository.java     ← 데이터 접근
```

**핵심 포인트**: 부가 로직을 Proxy로 분리, 핵심 로직은 깨끗해짐

```
Client → OrderServiceProxy(부가 로직) → OrderServiceImpl(핵심 로직)
```

```java
// ✅ 프록시가 부가 로직을 전담
public class OrderServiceProxy implements OrderService {
    private final OrderService target;   // 실제 객체

    public String getOrder(Long id) {
        // 부가 로직
        String result = target.getOrder(id);   // 핵심 로직 위임
        // 부가 로직
        return result;
    }
}
```

**남은 문제**: 메서드마다 프록시 코드 작성, Service마다 Proxy 클래스 필요

---

### Step 3: JDK Dynamic Proxy (`com.aop.step3`)

```
파일 구조:
  step3/
  ├── Main.java                 ← 실행
  ├── OrderService.java         ← 인터페이스
  ├── OrderServiceImpl.java     ← 핵심 로직
  ├── PaymentService.java       ← 두 번째 인터페이스 (재사용성 증명)
  ├── PaymentServiceImpl.java   ← 핵심 로직
  ├── LoggingHandler.java       ← ★ InvocationHandler (하나로 모든 Service에 적용!)
  └── OrderRepository.java      ← 데이터 접근
```

**핵심 포인트**: 하나의 LoggingHandler로 어떤 인터페이스든 프록시 자동 생성

```java
// ✅ 모든 메서드 호출이 이 하나의 메서드로 들어옴
public class LoggingHandler implements InvocationHandler {
    private final Object target;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 부가 로직 (실행 전)
        Object result = method.invoke(target, args);   // = Spring의 proceed()
        // 부가 로직 (실행 후)
        return result;
    }
}

// 사용: Proxy.newProxyInstance()로 동적 프록시 생성
OrderService proxy = (OrderService) Proxy.newProxyInstance(
    OrderService.class.getClassLoader(),
    new Class[]{OrderService.class},
    new LoggingHandler(realService)
);
```

**남은 문제**: 인터페이스 필수, Pointcut(어떤 메서드에 적용할지) 지정 불편

---

### Step 4: Spring AOP (`com.aop.step4`)

```
파일 구조:
  step4/
  ├── Main.java                 ← 실행 (Spring 컨테이너 사용)
  ├── AopConfig.java            ← @Configuration + @EnableAspectJAutoProxy
  ├── LoggingAspect.java        ← ★ @Aspect - 부가 로직 (선언적)
  ├── OrderService.java         ← 인터페이스
  ├── OrderServiceImpl.java     ← @Service - 핵심 로직
  ├── PaymentService.java       ← 인터페이스
  ├── PaymentServiceImpl.java   ← @Service - 핵심 로직
  └── OrderRepository.java      ← 데이터 접근
```

**핵심 포인트**: @Aspect + Pointcut으로 선언적 AOP, Spring이 프록시 자동 생성

```java
@Aspect
@Component
public class LoggingAspect {

    // Pointcut으로 적용 대상을 정확히 지정
    @Around("execution(* com.aop.step4.*ServiceImpl.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // 부가 로직 (실행 전)
        Object result = joinPoint.proceed();   // = Step3의 method.invoke()
        // 부가 로직 (실행 후)
        return result;
    }
}
```

---

## 8. 전체 비교 정리

| Step | 방식 | 특징 |
|------|------|------|
| Step 1 | AOP 없음 | 부가 로직 직접 작성 (중복) |
| Step 2 | 프록시 패턴 (수동) | 분리 성공, 프록시 직접 작성 |
| Step 3 | JDK Dynamic Proxy | 자동 프록시, 인터페이스 필수 |
| Step 4 | Spring AOP (@Aspect) | 선언적, Pointcut, 자동 프록시 |

| Step3 (Dynamic Proxy) | Step4 (Spring AOP) |
|----------------------|--------------------|
| `Proxy.newProxyInstance()` | Spring이 자동 생성 |
| `InvocationHandler.invoke()` | `@Around` 메서드 |
| `method.invoke(target, args)` | `joinPoint.proceed()` |
| 모든 메서드 무조건 적용 | Pointcut으로 선택 적용 |
| 인터페이스 필수 | 인터페이스 없이 클래스만 있어도 OK (CGLIB) |

> **프록시 방식 참고**: Spring AOP는 Target에 **인터페이스가 있으면 JDK 동적 프록시**,
> 없으면 **CGLIB**(클래스 상속 방식)을 사용한다. (단, Spring Boot 자동 설정을 쓰면 `proxy-target-class=true`가 기본이라 인터페이스가 있어도 CGLIB 사용.)  
> step4 예제는 인터페이스(OrderService)가 있고 `@EnableAspectJAutoProxy` 기본값을 쓰므로,
> `step4.Main` 실행 시 프록시 클래스명이 `jdk.proxy2.$Proxy##` 형태(JDK 동적 프록시)로 출력된다.
