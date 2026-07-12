package P5와일드카드타입파라미터;

public class 와일드카드타입파라미터Main {
    public static void main(String[] args) {
        // T extends Parent 가 아니라  <> 안의 T를 타입제한 한다는 것이다.
        //Applicant applicant=new Applicant(new Human());  //형변환 하면 되지만 raw타입쓰지마세요.
        Applicant<Human> humanApplicant = new Applicant<>(new Human());
        Applicant<Student> studentApplicant = new Applicant<>(new Student());
        Applicant<Worker> workerApplicant = new Applicant<>(new Worker());

        Course.register1(humanApplicant);
        //참고: 타입 소거 때문에 Applicant<Human>과 Applicant<Student>는 컴파일 후 같은 타입이 됨.
        //그래서 <>안의 타입만 다르게 해서 메소드 오버로딩하는건 불가능함.
        Course.register2(humanApplicant);
        Course.register3(humanApplicant);


        Course.register1(studentApplicant);
        Course.register2(studentApplicant);
        //Course.register3(studentApplicant);

        Course.register1(workerApplicant);
        Course.register2(workerApplicant);
        Course.register3(workerApplicant);

    }


}
