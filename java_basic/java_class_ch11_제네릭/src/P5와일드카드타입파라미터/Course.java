package P5와일드카드타입파라미터;

public class Course {
    public static void register1(Applicant<?> applicant){  //Applicant 누구나 와도 됨.
        System.out.println(applicant.getPerson().getClass().getSimpleName());
    }

    public static void register2(Applicant<? extends Human> applicant){
        //Applicant<?가 human하위클래스> 인것만 됨.Applicant<Human>, Applicant<Student>
        // Applicant< worker> 다됨
        System.out.println(applicant.getPerson().getClass().getSimpleName());

    }

    public static void register3(Applicant<? super Worker> applicant){
        //Applicant<worker>, Applicant<Human>만 됨
        System.out.println(applicant.getPerson().getClass().getSimpleName());
    }

}

