package 셋;

import java.util.Objects;

public class Person {
    public  int age;
    public  String name;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name);
        //기본적으로 모든필드가 같다면 해쉬코드도 같은걸로..
        //만약 필드가 같아도 해쉬에서 다른객체로 취급해서 저장하게 하고 싶으면
        // 그에 따른 hash알고리즘이 필요합니다..
    }


    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
