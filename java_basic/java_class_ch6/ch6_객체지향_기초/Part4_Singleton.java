package ch6_객체지향_기초;

public class Part4_Singleton {
    // 유일한 인스턴스를 저장할 정적 변수
    private static Part4_Singleton instance;

    // private 생성자 (외부에서 객체 생성 방지)
    private Part4_Singleton() {
        System.out.println("Singleton 객체 생성");
    }

    // 유일한 인스턴스를 반환하는 정적 메소드
    public static Part4_Singleton getInstance() {
        if (instance == null) {
            instance = new Part4_Singleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("싱글톤 패턴 예제입니다.");
    }

    public static void main(String[] args) {
        System.out.println("=== 싱글톤 패턴 ===");

        // Part4_Singleton s = new Part4_Singleton();  // 에러! private 생성자

        // getInstance()로만 객체 얻기
        Part4_Singleton s1 = Part4_Singleton.getInstance();
        Part4_Singleton s2 = Part4_Singleton.getInstance();
        Part4_Singleton s3 = Part4_Singleton.getInstance();

        // 모두 같은 객체인지 확인
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("s2 == s3: " + (s2 == s3));
        System.out.println("s1 == s3: " + (s1 == s3));

        s1.showMessage();
    }
}
