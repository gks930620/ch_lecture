package P4옵셔널과병렬;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

public class 옵셔널병렬Main {
    public static void main(String[] args) {
        List<Integer> ages = new ArrayList<>();
        ages.add(21);
        ages.add(21);
        ages.add(20);
        ages.add(19);
        ages.add(17);

        //average()는 double이 아니라 OptionalDouble을 반환함
        //리스트가 비어있으면 평균을 낼 수 없으니, "값이 없을 수도 있다"는 걸 타입으로 알려주는 것
        OptionalDouble average = ages.stream().mapToInt(age -> age).average();
        System.out.println("평균 : " + average.orElse(0)); //값이 없으면 0을 대신 사용

        //빈 리스트라면? orElse 덕분에 예외없이 0이 나옴
        List<Integer> empty = new ArrayList<>();
        OptionalDouble emptyAverage = empty.stream().mapToInt(age -> age).average();
        System.out.println("빈 리스트 평균 : " + emptyAverage.orElse(0));

        //Optional의 orElseThrow : 값이 없으면 예외를 던지고 싶을 때
        Optional<Integer> first = ages.stream().filter(age -> age > 20).findFirst();
        Integer 첫번째20초과 = first.orElseThrow(() -> new NoSuchElementException("20살 초과인 사람이 없습니다"));
        System.out.println("첫번째 20살 초과 : " + 첫번째20초과);

        //병렬 스트림 : parallelStream()만 쓰면 여러 스레드가 나눠서 처리해줌
        int sum = ages.parallelStream().mapToInt(age -> age).sum();
        System.out.println("병렬 합 : " + sum); //합처럼 순서와 무관한 집계는 결과가 같음

        //주의! 병렬 스트림은 처리(forEach) 순서가 보장 안 됨. 실행할 때마다 출력 순서가 달라질 수 있음
        System.out.println("--- 순차 스트림 forEach (항상 같은 순서) ---");
        ages.stream().forEach(age -> System.out.println(age));
        System.out.println("--- 병렬 스트림 forEach (순서 보장 안 됨) ---");
        ages.parallelStream().forEach(age -> System.out.println(age));

    }
}
