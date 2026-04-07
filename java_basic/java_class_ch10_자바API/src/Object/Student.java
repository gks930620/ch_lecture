package Object;

import java.util.Objects;

public class Student {
    public String name;
    public int age;
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override //객체의 메모리주소가 아닌 데이터값을 비교하기 위해서 재정의.
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }


    // equals에서 데이터값이 같아도 서로 다른 객체일 수도 있잖소. 그럴때 hashCode 사용
    // 데이터도 같고 서로 hashCode까지 같다면 같은 객체로 인식하기로 함. (HashSet,HashMap 등에서)
    // 물론 주소는 다르기때문에 ==은 false
    // 일반적으로 equals와 hashcode는 같이 재정의
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
