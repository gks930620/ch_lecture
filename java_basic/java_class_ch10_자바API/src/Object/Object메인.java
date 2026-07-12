package Object;

import java.util.HashSet;
import java.util.Set;

public class Object메인 {
    public static void main(String[] args) {
        //equals로 객체의 데이터 비교 ==> 같은객체로 판단
        // hashCode는 HashSet,HashMap 등에서 같은 데이터인지 아닌지 판단하는 기준
        // String의 경우 같은 문자열이면 hashCode도  같음.
        Student student1=new Student("한창희",30);
        Student student2=new Student("한창희",30);
        System.out.println(student1.equals(student2));
        System.out.println(student1==student2);


        String a1="aaa";
        String a2=new String("aaa");
        System.out.println(a1.hashCode()==a2.hashCode());
        //이러면 a1,a2는 hash셋에서 중복된 걸로 취급함.
        //Student도 hashCode를 Objects.hash(name,age)로 재정의했기 때문에
        //필드의 데이터가 같으면 hashCode도 같음 -> hashSet에서 중복으로 취급함.
        //(재정의를 안 하면 객체마다 hashCode가 달라서 중복으로 취급 안 함)
        Set<Student> set=new HashSet<>();
        set.add(student1);
        set.add(student2);
        System.out.println(set.size()); //중복으로 취급되어 1

    }
}
