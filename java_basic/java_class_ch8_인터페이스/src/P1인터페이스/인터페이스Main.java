package P1인터페이스;

public class 인터페이스Main {
    public static void main(String[] args) {
        //인터페이스의 역할
        // 선언방법 사용법은  상속과 비슷
        // 추상메소드는 반드시 구현해야 합니다.
        //인터페이스는 다중 구현 허용 (클래스는 단일 상속만 가능)
        // 타입변환, 다형성= 상속과 동일

        //상수필드, 디폴트 메소드, 정적 메소드, private 메소드, 객체타입확인(instanceof), 봉인(sealed)은
        // 참고 사항 — 지금 단계에서 깊이 알 필요 없음
        Idol idol = new Idol();
        Dancer dancer=new Dancer();
        Singer singer=new Singer();

        Arcademachine am=new Arcademachine();
        am.danceStage(idol);
        am.danceStage(dancer);
        //am.danceStage(singer);
        System.out.println("-------------------------");
        am.singStage(idol);
       // am.singStage(dancer);
        am.singStage(singer);



    }
}
