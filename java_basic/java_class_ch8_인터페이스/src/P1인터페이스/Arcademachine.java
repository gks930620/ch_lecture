package P1인터페이스;

public class Arcademachine {

    //매개변수 다형성
    public void danceStage(Danceable danceable){
            danceable.dance();
    }

    public void singStage(Singable singable){
        singable.sing();
    }
}
