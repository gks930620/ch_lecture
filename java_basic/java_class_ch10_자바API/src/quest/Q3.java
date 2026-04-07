package quest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Q3 {
    public static void main(String[] args) throws IOException {
        Path path = Path.of("sample_io.txt");
        Files.write(path, List.of("Java I/O", "file write/read test"));
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            System.out.println(line);
        }
    }
}
