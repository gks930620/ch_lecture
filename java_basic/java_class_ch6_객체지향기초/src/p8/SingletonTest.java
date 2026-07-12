package p8;

public class SingletonTest {
    public static void main(String[] args) {
        //싱글톤 : 프로그램 전체에서 객체를 딱 하나만 만들어 쓰는 패턴
        //만드는 순서
        //생성자를 private으로.
        // 필드에 static 필드로 객체하나 생성
        // public static getInstance 후 return 객체   ==> 끝
        Settings s1=Settings.getInstance();
        Settings s2=Settings.getInstance();
        System.out.println(s1==s2);   //true. 몇 번을 받아도 같은 객체
    }
}

class Settings {
    private static final Settings instance=new Settings();   //클래스에 딱 하나만 존재

    private Settings(){   //외부에서 new Settings() 불가능
    }

    public static Settings getInstance(){
        return instance;
    }
}
