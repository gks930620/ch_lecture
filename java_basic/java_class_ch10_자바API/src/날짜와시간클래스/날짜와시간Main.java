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
        LocalDate dNow= LocalDate.now();
        LocalTime tNow=LocalTime.now();
        LocalDateTime dtNow=LocalDateTime.now();

        LocalDate day= LocalDate.of(2004,05,7);
        LocalTime time=LocalTime.of(18,0,0);
        LocalDateTime dateTime= LocalDateTime.of(day,time);

        //각종 메소드
        System.out.println(dateTime.getDayOfYear());
        System.out.println(dateTime.getDayOfMonth());
        System.out.println(dateTime.getDayOfWeek());
        System.out.println(dateTime.getHour());
        System.out.println(dateTime.getMinute());
        //불변 객체라서 plusXxx는 기존 객체를 바꾸지 않고 새 객체를 리턴한다. 리턴값을 받아서 써야 함.
        System.out.println(dateTime.plusDays(3));
        System.out.println(dateTime.plusMonths(3));
        System.out.println(dateTime.plusHours(3));

        DateTimeFormatter dayFormatter=DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFormatter=DateTimeFormatter.ISO_LOCAL_TIME;
        System.out.println(dateTime); //그냥
        System.out.println(dayFormatter.format(dateTime) +" " + timeFormatter.format(dateTime));
        DateTimeFormatter myFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(myFormatter.format(dateTime));




    }
}
