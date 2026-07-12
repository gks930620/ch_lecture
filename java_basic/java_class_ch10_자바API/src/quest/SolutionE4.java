package quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

// E-4. 파일이 없을 때 예외 처리
public class SolutionE4 {
    public static void main(String[] args) {
        Path path = Path.of("no_such_file.txt");

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            System.out.println(br.readLine());
        } catch (NoSuchFileException e) {
            System.out.println("파일을 찾을 수 없습니다: " + e.getFile());
            System.out.println("경로를 확인한 뒤 다시 시도해 주세요.");
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류: " + e.getMessage());
        }
    }
}
// 출력:
// 파일을 찾을 수 없습니다: no_such_file.txt
// 경로를 확인한 뒤 다시 시도해 주세요.
