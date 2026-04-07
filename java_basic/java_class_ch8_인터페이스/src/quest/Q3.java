package quest;

public class Q3 {
    interface Clickable {
        void click();
    }

    public static void main(String[] args) {
        Clickable button = new Clickable() {
            @Override
            public void click() {
                System.out.println("익명 객체 클릭 이벤트 처리");
            }
        };

        button.click();
    }
}
