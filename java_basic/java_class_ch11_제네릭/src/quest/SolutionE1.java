package quest;

// E-1: new T()가 불가능한 예제와 이유
public class SolutionE1 {
    static class Factory<T> {
        public T create() {
            // return new T(); // 컴파일 오류: T는 런타임에 존재하지 않는 타입이다
            return null; // 임시 처리 (E-2에서 우회)
        }
    }

    public static void main(String[] args) {
        Factory<String> f = new Factory<>();
        System.out.println(f.create()); // null

        // 이유: 자바 제네릭은 타입 소거(Type Erasure) 방식이라 컴파일 후 T는 Object(또는 경계 타입)로
        // 바뀌고 타입 인자 정보가 사라진다. 런타임에는 T가 무엇이었는지 알 수 없으므로
        // new T(), T.class, new T[10]이 모두 금지된다.
    }
}
