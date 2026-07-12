package quest;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Q2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("목표일을 입력하세요 (yyyy-MM-dd) : ");
        String input = scanner.nextLine();

        try {
            LocalDate target = LocalDate.parse(input); //기본 형식이 yyyy-MM-dd
            LocalDate today = LocalDate.now();
            long days = ChronoUnit.DAYS.between(today, target);

            System.out.println("오늘: " + today);
            System.out.println("목표일: " + target);
            System.out.println("남은 일수: " + days);
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식이 잘못되었습니다. yyyy-MM-dd 형식으로 입력하세요.");
        }
    }
}
