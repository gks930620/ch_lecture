package quest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

// G-3. IOException을 도메인 예외로 변환해 전파
public class SolutionG3 {

    // 도메인 예외: unchecked + cause 보존
    static class FileProcessingException extends RuntimeException {
        public FileProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static void main(String[] args) {
        try {
            List<String> lines = loadReport(Path.of("no_such_report.txt"));
            lines.forEach(System.out::println);
        } catch (FileProcessingException e) {
            // 최상위: 사용자 안내 + 개발자 로그(cause 포함)
            System.out.println("[안내] 보고서를 불러오지 못했습니다. 관리자에게 문의하세요.");
            System.out.println("[LOG] " + e.getMessage() + " / cause=" + e.getCause());
        }
    }

    // 기술 예외(IOException)를 도메인 예외로 변환해 전파
    static List<String> loadReport(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileProcessingException("보고서 파일 처리 실패: " + path, e);
        }
    }
}
// 출력:
// [안내] 보고서를 불러오지 못했습니다. 관리자에게 문의하세요.
// [LOG] 보고서 파일 처리 실패: no_such_report.txt / cause=java.nio.file.NoSuchFileException: no_such_report.txt
