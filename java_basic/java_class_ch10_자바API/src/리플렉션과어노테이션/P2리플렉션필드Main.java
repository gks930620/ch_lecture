package 리플렉션과어노테이션;

import java.lang.reflect.Field;

public class P2리플렉션필드Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        Class<Square> clazz = Square.class;
        Class<?> clazz2 = Class.forName("리플렉션과어노테이션.Square");

        System.out.println("------------모든 필드에 대한 정보 ---------------");
        Field[] fileds= clazz.getDeclaredFields();
        for(Field field: fileds) {
            System.out.println("필드이름 : "+field.getName()+ "     필드 타입:"+field.getType());
        }

        System.out.println("-----------특정 필드에 대한 정보 ---------------");
        Field field= clazz.getDeclaredField("bottomLine");
        System.out.println("필드이름 : "+field.getName()+ "      필드 타입:"+field.getType());


    }
}
