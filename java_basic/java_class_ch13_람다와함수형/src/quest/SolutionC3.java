package quest;

import java.util.List;

// C-3: 생성자 참조(User::new)로 객체 리스트 생성
public class SolutionC3 {
    static class User {
        private final String name;
        User(String name) { this.name = name; }
        @Override public String toString() { return "User(" + name + ")"; }
    }

    public static void main(String[] args) {
        List<String> names = List.of("kim", "lee", "park");
        List<User> users = names.stream()
                .map(User::new) // name -> new User(name)
                .toList();
        System.out.println(users); // [User(kim), User(lee), User(park)]
    }
}
