package P1스트림이란;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 스트림메인 {
    public static void main(String[] args) {
        //스트림은 내부 반복자 방식 : 반복은 스트림 내부에 맡기고 우리는 처리 내용(람다)만 전달
        //스트림 파이프라인은 중간처리(가공)와 최종처리(결과 수집)로 구성됨
        //객체를 처리하는 Stream<T>와 함께 IntStream,LongStream,DoubleStream이 있음
        //스트림내부에서 외부변수 값 변경X, 참조타입의 상태변환(set메소드 등)은 가능
        //스트림 반복 시 continue, break, return 등 불가능.  이럴 때는 일반  for문 사용


        //List(및 컬렉션)를 이것저것 가공 잘 해야되는데 쉽게 가공해줄 수 있는 기술이 Stream입니다.

    }
}

