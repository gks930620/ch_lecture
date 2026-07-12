package quest;

import java.util.LinkedHashMap;
import java.util.Map;

// G-1: 전화번호부 시스템 (Map<String, Contact>)
public class SolutionG1 {
    record Contact(String name, String phone, String group) { }

    static class PhoneBook {
        private final Map<String, Contact> contacts = new LinkedHashMap<>(); // 등록 순서 유지

        void add(Contact c) {
            contacts.put(c.name(), c); // 같은 이름이면 덮어씀(수정 겸용)
        }

        Contact find(String name) {
            return contacts.get(name); // 없으면 null
        }

        boolean remove(String name) {
            return contacts.remove(name) != null;
        }

        void printAll() {
            for (Contact c : contacts.values()) {
                System.out.println(c.name() + " | " + c.phone() + " | " + c.group());
            }
        }
    }

    public static void main(String[] args) {
        PhoneBook book = new PhoneBook();
        book.add(new Contact("Kim", "010-1111-2222", "가족"));
        book.add(new Contact("Lee", "010-3333-4444", "회사"));
        book.add(new Contact("Park", "010-5555-6666", "친구"));

        System.out.println(book.find("Lee"));
        // Contact[name=Lee, phone=010-3333-4444, group=회사]

        book.remove("Kim");
        book.printAll();
        // Lee | 010-3333-4444 | 회사
        // Park | 010-5555-6666 | 친구

        System.out.println(book.find("없는사람")); // null
    }
}
