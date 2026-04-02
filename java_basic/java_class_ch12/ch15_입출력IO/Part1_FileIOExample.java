package ch15_입출력IO;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Part1_FileIOExample {
    public static void main(String[] args) {
        System.out.println("=== 파일 쓰기 (FileWriter) ===");
        try (FileWriter fw = new FileWriter("test.txt")) {
            fw.write("Hello, World!\n");
            fw.write("Java File I/O\n");
            fw.write("안녕하세요\n");
            System.out.println("파일 쓰기 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== 파일 읽기 (FileReader) ===");
        try (FileReader fr = new FileReader("test.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== NIO Files 사용 ===");
        try {
            // 파일 쓰기
            List<String> lines = Arrays.asList(
                "첫 번째 줄",
                "두 번째 줄",
                "세 번째 줄"
            );
            Files.write(Paths.get("nio_test.txt"), lines);
            System.out.println("NIO 파일 쓰기 완료");

            // 파일 읽기
            System.out.println("\nNIO 파일 읽기:");
            List<String> readLines = Files.readAllLines(Paths.get("nio_test.txt"));
            for (String line : readLines) {
                System.out.println(line);
            }

            // 파일 존재 확인
            boolean exists = Files.exists(Paths.get("nio_test.txt"));
            System.out.println("\n파일 존재? " + exists);

            // 파일 크기
            long size = Files.size(Paths.get("nio_test.txt"));
            System.out.println("파일 크기: " + size + " bytes");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== BufferedWriter (성능 향상) ===");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("buffered.txt"))) {
            for (int i = 1; i <= 5; i++) {
                bw.write("Line " + i);
                bw.newLine();  // 줄바꿈
            }
            System.out.println("버퍼링 쓰기 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

