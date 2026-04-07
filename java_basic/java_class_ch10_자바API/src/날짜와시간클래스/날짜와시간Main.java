package 날짜와시간클래스;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class 날짜와시간Main {
    public static void main(String[] args) {
        //진짜 날짜와시간은 할말이 많다. naver d2 localDate꼭 읽자.
        LocalDate dNow= LocalDate.now();
        LocalTime tNow=LocalTime.now();
        LocalDateTime dtNow=LocalDateTime.now();

        LocalDate day= LocalDate.of(2004,05,7);
        LocalTime time=LocalTime.of(18,0,0);
        LocalDateTime dateTime= LocalDateTime.of(day,time);

        //각종 메소드
        dateTime.getDayOfYear();
        dateTime.getDayOfMonth();
        dateTime.getDayOfWeek();
        dateTime.getHour();
        dateTime.getMinute();
        dateTime.plusDays(3);
        dateTime.plusMonths(3);
        dateTime.plusHours(3);

        DateTimeFormatter dayFormatter=DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormatter=DateTimeFormatter.ISO_LOCAL_TIME;
        System.out.println(dateTime); //그냥
        System.out.println(dayFormatter.format(dateTime) +" " + timeFormatter.format(dateTime));
        DateTimeFormatter myFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(myFormatter.format(dateTime));




    }
}
