package ch6_객체지향_기초;

public class Part3_StaticExample {
    // 인스턴스 변수 (각 객체마다 별도)
    private String name;

    // 정적 변수 (모든 객체가 공유)
    private static int count = 0;

    // 생성자
    public Part3_StaticExample(String name) {
        this.name = name;
        count++;  // 객체가 생성될 때마다 증가
    }

    // 인스턴스 메소드
    public void introduce() {
        System.out.println("이름: " + name);
        System.out.println("총 객체 수: " + count);
    }

    // 정적 메소드
    public static int getCount() {
        return count;
    }

    // 정적 메소드에서는 인스턴스 변수 사용 불가
    // public static void printName() {
    //     System.out.println(name);  // 에러!
    // }

    public static void main(String[] args) {
        System.out.println("=== static 키워드 ===");
        System.out.println("초기 객체 수: " + Part3_StaticExample.getCount());

        Part3_StaticExample obj1 = new Part3_StaticExample("객체1");
        System.out.println("\n객체1 생성 후:");
        obj1.introduce();

        Part3_StaticExample obj2 = new Part3_StaticExample("객체2");
        System.out.println("\n객체2 생성 후:");
        obj2.introduce();

        Part3_StaticExample obj3 = new Part3_StaticExample("객체3");
        System.out.println("\n객체3 생성 후:");
        obj3.introduce();

        System.out.println("\n최종 객체 수: " + Part3_StaticExample.getCount());
    }
}
