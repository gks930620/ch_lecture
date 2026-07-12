package quest;

// E-2: Class<T> 타입 토큰으로 new T() 제약 우회
public class SolutionE2 {
    static class TokenFactory<T> {
        private final Class<T> type; // 타입 토큰: 런타임까지 살아 있는 타입 정보

        TokenFactory(Class<T> type) { this.type = type; }

        public T create() throws ReflectiveOperationException {
            return type.getDeclaredConstructor().newInstance(); // new T() 대신 리플렉션
        }

        public boolean isInstance(Object obj) {
            return type.isInstance(obj); // instanceof T 대신 런타임 검증
        }
    }

    public static void main(String[] args) throws ReflectiveOperationException {
        TokenFactory<StringBuilder> factory = new TokenFactory<>(StringBuilder.class);

        StringBuilder sb = factory.create(); // 캐스팅 없이 T 타입으로 생성
        sb.append("token ok");
        System.out.println(sb);                          // token ok
        System.out.println(factory.isInstance(sb));      // true
        System.out.println(factory.isInstance("문자열")); // false

        // 핵심: Class<T> 객체는 소거되지 않고 런타임에 존재하므로
        // 생성(newInstance)과 타입 검증(isInstance)을 안전하게 대신할 수 있다. (타입 토큰 패턴)
    }
}
