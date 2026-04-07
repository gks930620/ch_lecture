# IoC / DI / Bean

---

## 0. IoC 개념 이해 (순수 Java → Spring) 

> **예제 코드**: `src/main/java/com/ioc/step1~4/` 패키지 참고  
> 각 step 패키지의 `Main.java`를 실행하면 됨

### Step 1. 직접 객체 생성 (`Step1_DirectCreation.java`)

```java
class MemberService {
    // ❌ 직접 생성 → 강한 결합
    private MemberRepository memberRepository = new MemberRepository();
}
```

**문제점:**
- Service가 구현체를 **직접 생성** → 강하게 결합됨
- Repository를 바꾸려면 Service 코드를 수정해야 함 (OCP 위반)
- 테스트 시 Mock으로 교체 불가능

---

### Step 2. 인터페이스 + 생성자 주입 (`Step2_ConstructorInjection.java`)

```java
interface MemberRepository {
    String findById(Long id);
}

class MemberService {
    private final MemberRepository memberRepository;

    // ✅ 생성자로 주입받음 → 어떤 구현체인지 모름
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}

// 사용하는 쪽 (main)
MemberRepository repo = new MemoryMemberRepository();  // 또는 new JpaMemberRepository();
MemberService service = new MemberService(repo);       // 외부에서 주입
```

**개선점:**
- Service는 **인터페이스에만 의존** → 구현체를 몰라도 됨
- 구현체 변경 시 Service 코드 수정 없음 → OCP 충족
- **제어의 역전(IoC)** 시작: "내가 만드는 게 아니라 받는다"

**남은 문제:**
- `main()`에서 직접 `new`로 조립 → 누군가는 여전히 구현체를 알아야 함

---

### Step 3. Assembler - 조립기 (`Step3_Assembler.java`)

```java
class AppAssembler {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MemberController memberController;

    public AppAssembler() {
        // ① 객체 생성
        this.memberRepository = new MemoryMemberRepository();  // 여기만 바꾸면 전체 교체!
        // ② 의존성 조립 (DI)
        this.memberService = new MemberService(memberRepository);
        this.memberController = new MemberController(memberService);
    }

    public MemberController getMemberController() { return memberController; }
    // ...getter
}

// 사용하는 쪽
AppAssembler assembler = new AppAssembler();
MemberController controller = assembler.getMemberController();  // 그냥 가져다 씀
```

**핵심:**
- 객체 생성 + 주입을 **Assembler 한 곳에서 전담**
- Service, Controller는 자기가 쓸 객체를 **직접 생성하지 않음** → IoC 완성
- 구현체 변경 시 **Assembler 한 곳만 수정**

```
┌─────────────────────────────────────────┐
│  Assembler (= Spring Container 원형)    │
│  ┌───────────────────────────────────┐  │
│  │ new MemoryMemberRepository()     │  │
│  │         ↓ 주입                    │  │
│  │ new MemberService(repository)    │  │
│  │         ↓ 주입                    │  │
│  │ new MemberController(service)    │  │
│  └───────────────────────────────────┘  │
└─────────────────────────────────────────┘
```

---

### Step 4. Spring 컨테이너 (`Step4_SpringContainer.java`)

Step3의 Assembler를 **Spring이 대신** 해준다.

```java
// @Configuration = Assembler의 Spring 버전
@Configuration
class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();        // 여기만 바꾸면 전체 교체
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());  // 생성자 주입
    }

    @Bean
    public MemberController memberController() {
        return new MemberController(memberService());  // 생성자 주입
    }
}

// 실행
AnnotationConfigApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

MemberController controller = context.getBean(MemberController.class);
```

**Step3 ↔ Step4 비교:**

| Step3 (수동 Assembler) | Step4 (Spring) |
|---|---|
| `AppAssembler` 클래스 | `@Configuration` (AppConfig) |
| `new 객체()` | `@Bean` 메서드 |
| `getter`로 조회 | `context.getBean()` |
| 직접 관리 | Spring 컨테이너가 관리 (**싱글톤**) |

---

## 1. IoC (Inversion of Control) - 제어의 역전

기존 방식에서는 개발자가 직접 객체를 생성하고 관리했다.

```java
// 기존 방식 - 개발자가 직접 생성 (Step1)
CommunityRepository repository = new CommunityRepositoryImpl();
CommunityService service = new CommunityService(repository);
```

IoC는 이 제어권을 **Spring 컨테이너(ApplicationContext)** 에게 넘기는 것이다.

```java
// IoC - Spring이 알아서 생성하고 주입 (Step4)
@Service
public class CommunityService {
    private final CommunityRepository communityRepository; // Spring이 넣어줌
}
```

> **정리**: Step1(직접 생성) → Step2(생성자 주입) → Step3(Assembler) → Step4(Spring 컨테이너)  
> "내가 객체를 만드는 게 아니라, 필요하면 Spring이 만들어서 줌"

---

## 2. Bean

Spring 컨테이너가 관리하는 객체를 **Bean** 이라고 한다.

### Bean 등록 방법

| 방법 | 설명 | 이 프로젝트 사용 예 |
|------|------|-------------------|
| `@Component` | 일반 컴포넌트 | `LocalFileStorage` |
| `@Service` | 서비스 계층 | `CommunityService`, `FileService` |
| `@Repository` | 데이터 계층 | `CommunityRepository` |
| `@Controller` | 웹 계층 | `CommunityController`, `UserController` |
| `@Configuration` + `@Bean` | 수동 등록 (= Step4) | `QuerydslConfig` |

```java
// QuerydslConfig.java - 수동 Bean 등록 (Step4 방식과 동일)
@Configuration
public class QuerydslConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em); // JPAQueryFactory를 Bean으로 등록
    }
}
```

> **참고**: `@Service`, `@Repository`, `@Controller`는 모두 내부에 `@Component`가 있다.  
> `@SpringBootApplication` 안의 `@ComponentScan`이 이 어노테이션들을 찾아서 자동 Bean 등록.

---

## 3. DI (Dependency Injection) - 의존성 주입

Bean이 다른 Bean을 사용할 때 Spring이 자동으로 주입해주는 것.

### 주입 방법 3가지

```java
// 1. 생성자 주입 (권장 - @RequiredArgsConstructor로 축약)
//    → Step2에서 배운 방식을 Lombok으로 축약한 것
@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository; // final + 생성자 주입
}

// 2. Setter 주입
@Service
public class SomeService {
    private SomeRepository someRepository;

    @Autowired
    public void setSomeRepository(SomeRepository repo) {
        this.someRepository = repo;
    }
}

// 3. 필드 주입 (테스트하기 어려워서 비권장)
@Service
public class SomeService {
    @Autowired
    private SomeRepository someRepository;
}
```

### 생성자 주입을 권장하는 이유

- `final` 키워드 → 불변 보장
- 순환 참조 컴파일 시점에 감지
- 테스트 시 Mock 주입 용이 (Step2에서 직접 해봤듯이)

---

## 4. ApplicationContext (Spring 컨테이너)

```
Spring 컨테이너 (ApplicationContext) ← Step4의 AnnotationConfigApplicationContext
├── CommunityService Bean
├── CommunityRepository Bean
├── FileService Bean
├── UserController Bean
├── JPAQueryFactory Bean
└── ...
```

- 서버 시작 시 `@ComponentScan`으로 Bean 전부 등록
- `DemoApplication.java`의 `@SpringBootApplication` 안에 `@ComponentScan` 포함됨
- Step3의 Assembler와 같은 역할을 **자동으로** 수행

---

## 5. 이 프로젝트에서의 Bean 흐름

```
CommunityController
    └── CommunityService    (@Service Bean)
            └── CommunityRepository  (@Repository Bean)
                    └── JPAQueryFactory  (@Bean - QuerydslConfig)

UserController
    └── UserRepository  (@Repository Bean)

FileController
    └── FileService  (@Service Bean)
            └── FileRepository  (@Repository Bean)
```

---

## 6. 학습 순서 요약

| 단계 | 패키지 / 파일 | 핵심 |
|------|------|------|
| Step1 | `com.ioc.step1` (Main, MemberRepository, MemberService) | 직접 생성 → 강한 결합, 문제점 인식 |
| Step2 | `com.ioc.step2` (Main, MemberRepository(I), Memory/Jpa구현체, MemberService) | 인터페이스 + 생성자 주입 → IoC의 시작 |
| Step3 | `com.ioc.step3` (Main, AppAssembler, MemberController 등) | Assembler 도입 → IoC 컨테이너 원형 |
| Step4 | `com.ioc.step4` (Main, AppConfig, MemberController 등) | Spring 컨테이너 → Assembler를 Spring이 대신 |
| 실무 | 이 프로젝트 전체 (`com.ch.basic`) | `@Service` + `@RequiredArgsConstructor` → 자동 |

