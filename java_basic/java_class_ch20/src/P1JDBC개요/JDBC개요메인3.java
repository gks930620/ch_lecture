package P1JDBC개요;

import java.sql.*;

public class JDBC개요메인3 {
    public static void main(String[] args) {
        try {
            Class.forName("드라이버");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //class.forName 내용들은 블로그참고해도 좋음 기발개발class.forName

        //프로그램도중 Hello.class 만드는 과정에서 일어나는 일들 설명.
        //로드타임, 런타임 동적 로딩 설명

        //Class.forName설명하기전에  자바 프로그래밍 실행 과정
        //런타임 도중  어떤 클래스가 있으면  그 클래스(에 관한 객체)를 로드해야함.
        // 로드한다음 실행.  이 때 로드하는 과정에서 실행되는 코드가 static{}구문




    }
}
