package P4comparator;

public class Student{
    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    // 예제 단순화를 위해 public 필드 사용 (실무에서는 private + getter/setter 권장, ch6 참고)
    public String name;
    public int age;
    public int score;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }


}
