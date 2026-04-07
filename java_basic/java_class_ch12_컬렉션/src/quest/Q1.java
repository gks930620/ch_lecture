package quest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Q1 {
    public static void main(String[] args) {
        List<String> list = List.of("A", "B", "A", "C", "B", "D");
        List<String> unique = new ArrayList<>(new LinkedHashSet<>(list));
        System.out.println(unique);
    }
}
