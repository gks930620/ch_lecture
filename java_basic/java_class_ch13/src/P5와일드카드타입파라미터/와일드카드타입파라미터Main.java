package P5와일드카드타입파라미터;

public class 와일드카드타입파라미터Main {
    public static void main(String[] args) {
        // T extends Parent 가 아니라  <> 안의 T를 타입제한 한다는 것이다.
        //Applicant applicant=new Applicant(new Human());  //형변환 하면 되지만 raw타입쓰지마세요.
        Applicant<Human> humanApplicant = new Applicant<>(new Human());
        Applicant<Student> studentApplicant = new Applicant<>(new Student());
        Applicant<Worker> workerApplicant = new Applicant<>(new Worker());

        Course.register1(humanApplicant);  //Applicant<>에서 <>따라 달라지는것까지 오버로딩X
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
