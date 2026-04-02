public class Part2_CompileTest {
    public static void main(String[] args) {
        System.out.println("=== 컴파일과 실행 테스트 ===");
        System.out.println("JDK 버전: " + System.getProperty("java.version"));
        System.out.println("OS: " + System.getProperty("os.name"));
        System.out.println("JVM 이름: " + System.getProperty("java.vm.name"));

        System.out.println("\n바이트코드가 JVM에 의해 실행되고 있습니다!");
    }
}

