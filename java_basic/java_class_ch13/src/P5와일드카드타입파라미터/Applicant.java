package P5와일드카드타입파라미터;

public class Applicant<T> {
    private T person ;

    public Applicant(T person) {
        this.person = person;
    }

    public T getPerson() {
        return person;
    }
}
