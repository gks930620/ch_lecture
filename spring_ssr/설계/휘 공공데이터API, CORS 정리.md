# 공공 API 요청
-  공공데이터 API로 요청해야되는건 실시간( 시간단위를 떠나서... )으로 변하는 데이터.   예를 들어  날씨, 미세먼지, 환율 등등
- 1년에 한번정도다?? 굳이 API요청보다는 그냥 파일 다운받아서 DB에 넣는식이면 ㅇㅋ


 # Spring API  여러방식 정리
| | RestTemplate | WebClient | RestClient | HttpInterface | Java HttpClient | OpenFeign |
|---|---|---|---|---|---|---|
| **Spring 버전** | 3.0+ | 5.0+ | 6.1+ | 6.0+ | Java 11+ | 외부 라이브러리 |
| **방식** | 동기 | 비동기/동기 | 동기 | 동기/비동기 | 동기/비동기 | 동기 |
| **선언형** | ❌ | ❌ | ❌ | ✅ | ❌ | ✅ |
| **상태** | ⚠️ Deprecated | 유지 | ⭐ 권장 | ⭐ 권장 | 표준 내장 | 유지 |
| **난이도** | 쉬움 | 복잡 | 쉬움 | 매우 쉬움 | 중간 | 매우 쉬움 |
| **Spring 의존성** | ✅ | ✅ | ✅ | ✅ | ❌ | ❌ |
| **추가 의존성** | 없음 | webflux 필요 | 없음 | 6.1+는 없음 ✅ | 없음 | spring-cloud 필요 |


참고  :  javascript가 비동기 요청하는건 위의 비동기/동기랑 상관없음.
서버의 컨트롤러 내에서 비동기/동기 처리를  말함.
// 동기 - 순서대로 기다림 (총 300ms)
User user = restClient.get().uri("/users/1").retrieve().body(User.class);      // 100ms 대기
Order order = restClient.get().uri("/orders/1").retrieve().body(Order.class);  // 100ms 대기
Point point = restClient.get().uri("/points/1").retrieve().body(Point.class);  // 100ms 대기
// 3개를 순서대로 기다리니까 총 300ms

// 비동기 - 동시에 요청 (총 100ms)
Mono<User> user = webClient.get().uri("/users/1").retrieve().bodyToMono(User.class);
Mono<Order> order = webClient.get().uri("/orders/1").retrieve().bodyToMono(Order.class);
Mono<Point> point = webClient.get().uri("/points/1").retrieve().bodyToMono(Point.class);

대부분은 동기방식이면 되고 , 비동기가 필요한 상황은
- 여러 외부 API를 동시에 호출해야 할 때
- 트래픽이 매우 많은 서비스 (스레드 효율 극대화)
- 대용량 데이터 스트리밍 처리

그래서 동기방식으로 API 요청을 한다고 하면 RestClient나 HttpInterface를 이용.
API 요청  처음에는 RestClient로 하고,  나중에 API 요청이 많아진다면 HttpInterface 고려.
비동기 요청이 필요할 때는 webclient + HttpInterface 조합이 고려



# CORS : 내 spring 서버는   개발자가 의도한 대로만 동작,  브라우저는 해커가 조작가능.    
그래서 기본적으로 브라우저는 다른 오리진으로의 요청을 막음.    (오리진 : protocol + host + port)
브라우저가  오리진 다른 요청 막는 건 javascript  ajax 등의 비동기 요청.
a<img>나  url직접입력, a태그 등은 안 막음
공공API 서버는 다른 서버의 요청을 CORS로 막는게 아님..  다른서버의 요청 막는건 인증키 (외 ip 등 기타 여러가지)

SOP (Same-Origin Policy) : 브라우저가 다른 오리진으로의 요청을 막는 보안 정책
CORS (Cross-Origin Resource Sharing) : 브라우저가 다른 오리진으로의 요청을 허용할지 말지 결정하는 메커니즘.
어쨋든 CORS 막음 = CORS 허용안함 = 브라우저가 javascript  ajax  막음

참고 : ajax, fetch, xhr  등 막음.  xhr은 동기요청도 가능한데 막음.  
         정확히는 비동기 요청을 막는다기보다는  자바스크립트가 응답을 읽을 수 있는 모든 행위에 대해 CORS 검사 후  브라우저가 막음. 

## CORS 동작원리 =  브라우저가 javascript ajax 요청 막는 과정
1. 브라우저의 javascript ajax =>  공공API 서버 요청
2.  브라우저는 먼저 공공API한테 물어봄. 첫번째요청.
     나 a 오리진인데 공공API야 너한테 요청해도 돼?  (preflight 요청)
3.  공공API 서버는  브라우저한테  a 오리진은 CORS 허용 안함!!   이라고 응답.   (사실  a 오리진 허용안함이 아니라 , 허용목록에 a 오리진 없다고 응답)
4. 브라우저는  공공API 서버의 응답 보고  a 오리진은 CORS 허용 안함!!   그래서 브라우저는  javascript ajax 요청 막음.

 반대로
 3번에서 공공API 서버가  a 오리진은 CORS 허용함!!이라고 응답하면 
 4번에서 브라우저는  a 오리진은 CORS 허용하는군.    그래서 javascript  ajax 요청 허용함. (2번째 요청. )
참고 : 몇몇 공공API는 모든 오리진을 허용하기 때문에  브라우저 javascript ajax 에서 직접요청해도 CORS  문제 안 걸릴수도 있음.
참고2 : preflight 요청 없이 바로 요청하느 Simple Request도 있음.  이 때는 2번 과정 없이 바로 3번으로 넘어감

##  공공APi 요청 시 CORS 해결 방법
- 가장 정석적인 프록시 서버(내 스프링 서버) 이용
- 공공API 서버가 CORS 허용 안 된 상태
1. 브라우저의 javascript => 공공API 서버.      브라우저가  기본적으로 막음
2. 브라우저의 javascript => 내 spring 서버 => 공공API 서버
   내 spring 서버에서 공공API 서버로 요청 보낼 때는 CORS 문제 없음.
   (CORS는 브라우저가 자바스크립트 막는거지 서버는 서버에서의 요청을 막는게 아니다!!! )
   즉 내spring 서버 => 공공API서버는 CORS랑 전혀 상관없음!!
   서버가 서버 요청 막는건 CORS가 아니라 IP나 인증키 등등

여기까지는 공공API 에 요청할 때 문제되는 COSR에 관한 내용.
-----------------------------------------------------------------


# 서버(Spring)개발자가 알아야 할 CORS와 CSRF
## 단순 공공API 호출이 아니라 우리회사 여러  서버가 연동되는 상황에서의 CORS
1.  화면html주는 서버(같은회사의  8030) => 브라우저 javascript ajax=> 내 spring 서버( 같은회사의 8080)              
2.  브라우저가 javascript ajax 요청 하는 과정은
    브라우저의 javascript ajax =>  내 spring  서버 요청. 브라우저는 먼저 공공API한테 물어봄.
    나 8030오리진인데  spring 서버(8080) 너한테 요청해도 돼?  (preflight 요청)
그럼 내가 spring 서버(8080) 개발자고  저 8030 오리진은 같은회사의 요청이니까 허용을 해야됨.
그래서 allowedOrigins 설정에서 8030 오리진을 허용해주면 브라우저는 8030 오리진은 CORS 허용하는군. 
그래서 javascript  ajax 요청 허용함. (2번째 요청. )
보통 8030서버개발자랑 8080서버개발자가 다르면  
화면html주는 8030서버개발자가 8080서버개발자한테  "야 우리 화면에서 8080서버로 ajax 요청할건데 CORS 허용 좀 해줘" 
라고 요청해서 8080서버개발자가 allowedOrigins 설정에서 8030오리진을 허용해주는 식으로 협업이 이루어짐

    
##  preflght 없이 Simple Request 보내는 조건
-메서드: PUT, DELETE, PATCH, CONNECT 등 (GET/POST/HEAD가 아닌 것)
-헤더: 내가 임의로 만든 헤더가 포함된 경우 (예: X-AUTH-TOKEN, Authorization)
-Content-Type: 다음 세 가지가 아닌 경우  application/x-www-form-urlencoded ,multipart/form-data  , text/plain

## Spring 설정별 CORS 동작 비교
> 전제: **B 오리진**에서 내 Spring 서버로 요청하는 상황

| 설정 | 구분 | Simple Request (B 오리진) | Preflight (B 오리진) |
|---|---|---|---|
| **1. 아무것도 설정 안 함** | 브라우저 동작 | 예비 요청 없이 본 요청을 바로 보냄 | `OPTIONS` 요청을 먼저 보냄 |
|  | 서버(Spring) 동작 | 검사 로직이 없으므로 그냥 통과 | `OPTIONS` 요청에 대해 정상적인 CORS 응답 헤더를 주지 않음 |
|  | 컨트롤러 실행 | **실행 됨** (DB 수정/삭제 발생 가능) | **실행 안 됨** (예비 요청 응답에 허용 헤더가 없어서 본 요청이 안 감) |
|  | JS 결과 | **에러** (서버는 실행했지만 브라우저가 응답을 JS에 안 넘김) | **에러** (예비 요청 단계에서 본 요청 차단) |
| **2. A 오리진만 허용** | 브라우저 동작 | 예비 요청 없이 본 요청을 바로 보냄 | `OPTIONS` 요청을 먼저 보냄 |
|  | 서버(Spring) 동작 | `CorsFilter`가 `Origin: B`를 확인하고 차단 | `CorsFilter`가 `OPTIONS` 요청을 받고 거부 |
|  | 컨트롤러 실행 | **실행 안 됨** (`403 Forbidden`) | **실행 안 됨** (본 요청 자체가 안 감) |
|  | JS 결과 | **에러** (서버가 거부함) | **에러** (예비 요청에서 탈락) |
| **3. Allow All (*)** | 브라우저 동작 | 예비 요청 없이 본 요청을 바로 보냄 | `OPTIONS` 요청을 먼저 보냄 |
|  | 서버(Spring) 동작 | 누구나 허용이므로 그냥 통과 | `OPTIONS` 응답에 `Access-Control-Allow-Origin: *` 등을 담아 응답 |
|  | 컨트롤러 실행 | **실행 됨** | **실행 됨** (예비 요청 통과 후 본 요청 실행) |
|  | JS 결과 | **성공** (`200 OK`) | **성공** (`200 OK`) |

> 핵심:
> - **CORS 미설정**이라고 해서 서버가 자동으로 막히는 것은 아니다.
> - **Simple Request**는 서버에서 실제 처리까지 될 수 있고, 그 뒤에 브라우저가 JS에게 응답을 차단할 수 있다.
> - **Preflight**는 본 요청 전에 브라우저가 먼저 확인하므로, 허용되지 않으면 본 요청 자체가 가지 않는다.

CORS가 허용되지 않는 데이터를 볼 수없게 방어한다는건 브라우저가 js한테 응답을 안 보내는 것이고, 
서버개발자 입장에서는  1번 상황에서처럼  악의적인 사용자가 javascript ajax로   데이터 변경을 하면 안되니까 
믿을만한 오리지만 허용해주도록 잘 설정해야됨.
그래서 서버개발자인 나는 믿을만한 A 오리진만 허용하도록 CORS를 설정해서 
B 오리진에서 javascript ajax로 데이터 변경하는 걸 막았는데... 그럼 끝인가???

아니지...  B오리진 사이트에서  내 서버의 데이터변경용 URL을  form 태그로 요청한다면???

## CSRF
그래서 나온게 CSRF 입니다.
CSRF 설정을 하면 POST 요청이 왔을 때 CSRF 토큰이 없으면 403 Forbidden

내 사이트의 화면에서는  서버에서 만든 CSRF 토큰이 전달 됩니다.
<form action="https://bank.com/transfer" method="POST">
  <input name="_csrf" value="CSRF토큰값"> 
  <input name="amount" value="1000000">
  <input name="to" value="my">
  <button>이벤트 당첨! 클릭하세요</button>
</form>

해커의 사이트에서 이런 버튼을 만들고 사용자가 버튼을 누른다면  데이터가 변경이 됩니다. 
<form action="https://bank.com/transfer" method="POST">
  <input name="amount" value="1000000">
  <input name="to" value="hacker">
  <button>이벤트 당첨! 클릭하세요</button>
</form>
저런 form 태그로 POST 요청이 오면 CSRF 토큰이 없으니까 403 Forbidden 뜨면서 데이터 변경 안됨.


참고 : CSRF는 GET 요청에는 적용 안됨.   GET 요청은 애초에 상태 변경없이 하는게 HTTP 설계 원칙이니까
참고 : CSRF는 자바스크립트도 막음. 애초에 post요청에서 토큰이 있냐 없냐를 검사하는거니까...
         A 오리진에서 자바스크립트 요청을 할 때  CSRF 토큰 같이 보내면 됨.
이렇게 하니까 정리하긴 했는데 .. 나만 이해한거 같아.. 다른사람 이해 못할거같음..  그림 ppt 등으로 확실하게 빡 뭔가 이해시켜야 될듯 
 




와... 근데 APi 너무 좋다  
간단하게 사이트 만들자  = > 일정입력하면 날씨 좋은 곳 위주로 추천 



일단 사택부터 하고 하자.. 주말에 하면 많이 할 수 있겠지..
일단 넘어가고  공공API 요청하는거 AI한테 부탁해서 얼른 끝내보자.







