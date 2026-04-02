package Object;

public class Object메인2 {
    public static void main(String[] args) {
        //레코드,롬복은 패스
        Student student1=new Student("한창희",30);
        System.out.println(student1); //toString을 재정의하면 주소값대신 내가 원하는대로 나옴

    }
}
