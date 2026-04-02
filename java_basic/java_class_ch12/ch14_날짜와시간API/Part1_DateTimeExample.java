package ch14_날짜와시간API;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Part1_DateTimeExample {
    public static void main(String[] args) {
        System.out.println("=== LocalDate (날짜) ===");
        LocalDate today = LocalDate.now();
        System.out.println("오늘: " + today);
        System.out.println("년: " + today.getYear());
        System.out.println("월: " + today.getMonthValue());
        System.out.println("일: " + today.getDayOfMonth());
        System.out.println("요일: " + today.getDayOfWeek());

        LocalDate birthday = LocalDate.of(2000, 1, 1);
        System.out.println("생일: " + birthday);

        System.out.println("\n=== LocalTime (시간) ===");
        LocalTime now = LocalTime.now();
        System.out.println("현재 시간: " + now);
        System.out.println("시: " + now.getHour());
        System.out.println("분: " + now.getMinute());
        System.out.println("초: " + now.getSecond());

        LocalTime meetingTime = LocalTime.of(14, 30);
        System.out.println("회의 시간: " + meetingTime);

        System.out.println("\n=== LocalDateTime (날짜+시간) ===");
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println("현재: " + nowDateTime);

        LocalDateTime appointment = LocalDateTime.of(2024, 3, 15, 14, 30);
        System.out.println("약속: " + appointment);

        System.out.println("\n=== 날짜 계산 ===");
        LocalDate tomorrow = today.plusDays(1);
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate nextMonth = today.plusMonths(1);
        LocalDate nextYear = today.plusYears(1);

        System.out.println("내일: " + tomorrow);
        System.out.println("다음 주: " + nextWeek);
        System.out.println("다음 달: " + nextMonth);
        System.out.println("내년: " + nextYear);

        System.out.println("\n=== 시간 계산 ===");
        LocalTime after2Hours = now.plusHours(2);
        LocalTime before30Min = now.minusMinutes(30);

        System.out.println("2시간 후: " + after2Hours);
        System.out.println("30분 전: " + before30Min);

        System.out.println("\n=== Period (날짜 간격) ===");
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        Period period = Period.between(start, end);

        System.out.println("기간: " + period);
        System.out.println("개월: " + period.getMonths());
        System.out.println("일: " + period.getDays());

        System.out.println("\n=== Duration (시간 간격) ===");
        LocalTime time1 = LocalTime.of(9, 0);
        LocalTime time2 = LocalTime.of(17, 30);
        Duration duration = Duration.between(time1, time2);

        System.out.println("근무 시간: " + duration.toHours() + "시간 " +
                          (duration.toMinutes() % 60) + "분");

        System.out.println("\n=== 포맷팅 ===");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a h:mm");

        String formatted1 = nowDateTime.format(formatter1);
        String formatted2 = nowDateTime.format(formatter2);

        System.out.println("포맷1: " + formatted1);
        System.out.println("포맷2: " + formatted2);
    }
}

