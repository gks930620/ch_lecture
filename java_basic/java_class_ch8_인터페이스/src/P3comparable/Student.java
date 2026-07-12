package P3comparable;

public class Student  implements Comparable<Student>{
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

    @Override
    public int compareTo(Student anotherStd) {
        if( this.age> anotherStd.age) return -1;
        else if( this.age<anotherStd.age) return 1;
        else{ //나이도 같다면
            if(this.score >anotherStd.score) return -1;
            else if (this.score<anotherStd.score) return 1;
            else return 0;
        }
    }
}
