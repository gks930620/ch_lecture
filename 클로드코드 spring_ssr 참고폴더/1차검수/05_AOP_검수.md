# 검수: 개념_10_AOP + com/aop 소스

> 대조 소스: `com/aop/step1~4` 전체, build.gradle

## 소스·문서 자체 손상 (최우선 — 검수자 직접 재확인 완료)

### [높음] 개념_10_AOP.md — 한글 인코딩이 파일 자체에서 깨져 있음 (복구 불가 상태)
- 파일 바이트 확인 결과 U+FFFD 대체 문자(`EF BF BD`)와 `?`가 **133개 줄**에 섞여 저장됨. 뷰어 문제가 아니라 디스크의 파일 자체가 손상된 상태이며 HEAD 커밋 버전도 동일하게 손상됨 (같은 폴더의 개념_01, 개념_09, 개념_11 등은 정상).
- 예: 116행은 원래 `log.info("메서드 실행 후");`였을 것이 `log.info("메서???�행 ??);`로 **닫는 따옴표까지 깨져 코드도 문법 오류** 상태.
- **수정 제안**: 손상 이전 백업에서 복원하거나 원본 재작성. 현재 상태로는 초보자가 읽을 수 없음.

### [높음] com/aop/step1~step3 — .java 15개 파일 전부 0바이트 빈 파일
- step1(3개), step2(5개), step3(7개) 전부 0바이트이며 git 인덱스에도 빈 blob으로 커밋됨. 문서 8~15행("step1.Main 실행" 등)과 203~303행의 step1~3 코드 예제(로깅 중복 → OrderServiceProxy → LoggingHandler/Proxy.newProxyInstance)를 대조할 실제 코드가 없고, 수강생이 `step1.Main`~`step3.Main`을 실행하면 클래스가 없어 실행 불가. (step4 8개 파일은 정상.)
- **수정 제안**: step1~3 소스 복원·커밋, 복원 전까지 문서에 "코드 준비 중" 표시. 파일명 골격은 문서의 파일 구조 목록과 정확히 일치하므로 문서 기준으로 복원 가능.

## 문서 내용 (인코딩 복원/재작성 시 함께 반영)

### [중간] 93행 — Pointcut 예시 `execution(* *Service.*(..))`가 의도대로 동작하지 않음
- AspectJ 타입 패턴에서 `*`는 패키지 구분자(`.`)를 포함하지 않으므로 `*Service`는 **기본(default) 패키지**의 클래스만 매칭. 160행의 `execution(* *..*Service.*(..))`가 올바른 형태.
- **수정 제안**: 93행도 `*..*Service`로 통일.

### [중간] 161행 — `@annotation(Transactional)` 예시는 실제로는 매칭되지 않음
- 포인트컷 표현식 문자열 안에서는 import가 적용되지 않아 FQN 필요.
- **수정 제안**: `@annotation(org.springframework.transaction.annotation.Transactional)`.

### [중간] 362행 — §8 비교표 "Step4 = 클래스만 있어도 OK (CGLIB)"가 실제 실행 결과와 모순
- step4는 인터페이스(OrderService/PaymentService)가 있고 AopConfig의 `@EnableAspectJAutoProxy`가 기본값(proxyTargetClass=false)이므로 실제로는 **JDK 동적 프록시**가 생성됨. Main.java:50-51이 프록시 클래스명을 출력하는데 수강생은 CGLIB이 아니라 `jdk.proxy2.$Proxy##`를 보게 되어 표와 모순.
- **수정 제안**: "Spring AOP는 인터페이스가 있으면 JDK 프록시, 없으면 CGLIB(단, Spring Boot 웹앱 기본은 CGLIB). 이 예제는 인터페이스가 있어 JDK 프록시가 출력됨" 1~2문장 보충.

### [낮음] 87-97행 — 핵심 용어 표에 Weaving(위빙) 누락
- Aspect/Advice/Pointcut/JoinPoint/Target/Proxy만 있고, "런타임에 프록시로 위빙"한다는 개념이 문서 어디에도 없음.

### [낮음] (문서 전체) — Spring AOP의 한계(자기 호출) 설명 부재
- 같은 클래스 내부 `this.method()` 호출은 프록시를 거치지 않아 AOP(특히 @Transactional) 미적용, private/final 메서드 미적용 등 실무 필수 함정이 없음. §6에서 @Transactional=AOP라고 소개하는 만큼 "Spring AOP 주의사항" 섹션 추가 권장. (개념_06_Transaction.md에는 자기호출 설명이 있으므로 상호 링크도 방법.)

## 문제 없음 확인
- step4 코드 일치: 문서 306~336행의 파일 구조(8개), 클래스명, `@Aspect`/`@Component`, `logExecutionTime(ProceedingJoinPoint)`, 포인트컷 `execution(* com.aop.step4.*ServiceImpl.*(..))` — 실제 LoggingAspect.java와 정확히 일치
- 포인트컷 해석(반환타입/클래스/메서드/파라미터) — 실제 동작과 일치
- §8 비교표 내용은 Main.java:76-83 출력 요약과 동일, 실행 순서 일치
- §1 실무 예시의 CommunityService, FileService — 실제 존재
- AOP 의존성 `spring-boot-starter-aop` — build.gradle:42 존재
- Advice 5종(@Before/@After/@AfterReturning/@AfterThrowing/@Around), @Transactional 프록시 흐름, Step3 한계(인터페이스 필수) 설명 — 기술적으로 정확
