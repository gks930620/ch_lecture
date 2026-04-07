package 리플렉션과어노테이션;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class P4Class로부터객체얻기메소드사용Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<Square> clazz = Square.class;

        System.out.println("-----------특정 메소드 얻어서 사용하기-----------------------");
        Method methodGetArea= clazz.getDeclaredMethod("getArea");
        Method methodStaticGetArea=clazz.getDeclaredMethod("staticGetArea",int.class,int.class);
        // 이런식으로 메소드 얻으면 된다. 파라미터 쓰는 방법만 유의깊게 보자.
        Square square= (Square)clazz.newInstance();
        square.setBottomLine(5); square.setHeight(20);
        System.out.println("square 객체의 넓이 : "+methodGetArea.invoke(square));
        System.out.println("static메소드 넓이 구하기 : "+methodStaticGetArea.invoke(null,10,20));

        //모든메소드 얻기 + 반복문으로 해당 메소드 실행하기

    }
}
