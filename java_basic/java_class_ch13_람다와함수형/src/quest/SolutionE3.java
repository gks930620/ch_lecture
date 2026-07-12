package quest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// E-3: 이벤트 콜백 구조를 람다로 단순화 (콜백 = 이벤트 값을 소비 -> Consumer<T>)
public class SolutionE3 {
    static class Button {
        private final List<Consumer<String>> listeners = new ArrayList<>();
        void onClick(Consumer<String> listener) { listeners.add(listener); }
        void click(String who) { listeners.forEach(l -> l.accept(who)); }
    }

    public static void main(String[] args) {
        Button button = new Button();
        button.onClick(who -> System.out.println(who + " 클릭 로그 저장"));
        button.onClick(who -> System.out.println(who + "에게 화면 갱신 알림"));

        button.click("kim");
        // kim 클릭 로그 저장
        // kim에게 화면 갱신 알림
    }
}
