package 리플렉션과어노테이션;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class P3리플렉션메소드Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        Class<Square> clazz = Square.class;
        Class<?> clazz2 = Class.forName("리플렉션과어노테이션.Square");

        System.out.println("-----------모든 메소드에 대한 정보 ---------------");
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {
            System.out.println("메소드이름 : "+method.getName()
                    +"     메소드 파라미터 수:"+method.getParameterCount()
                    +"     메소드 리턴 타입"+ method.getReturnType());
        }
        System.out.println("-----------특정 메소드에 대한 정보 ---------------");
        Method methodGetArea= clazz.getDeclaredMethod("getArea");
        Method methodStaticGetArea=clazz.getDeclaredMethod("staticGetArea",int.class,int.class);

    }
}
