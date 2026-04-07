package quest;

import java.util.ArrayList;
import java.util.List;

public class Q4 {
    static void copyNumbers(List<? extends Number> from, List<? super Number> to) {
        to.addAll(from);
    }

    public static void main(String[] args) {
        List<Integer> source = List.of(1, 2, 3);
        List<Number> target = new ArrayList<>();
        copyNumbers(source, target);
        System.out.println(target);
    }
}
