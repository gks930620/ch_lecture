package P1상속기본;

public class Animal {
    // 예제 단순화를 위해 public 필드 사용 (실무에서는 private + getter/setter 권장, ch6 참고)
    public String name;
    public int age;

    // 부모가 제공하는 기본 동작. 자식이 그대로 물려받거나, 재정의(오버라이딩)할 수 있다.
    public void sound() {
        System.out.println(name + "가 소리를 냅니다.");
    }

    public void info() {
        System.out.println("이름: " + name + ", 나이: " + age);
    }
}
