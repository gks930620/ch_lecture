package P1JDBC개요;

import java.sql.*;

public class JDBC개요메인2 {
    public static void main(String[] args) {
        try {
            Class.forName("드라이버");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        //우리가 DB관련 프로그래밍을 전부 Connection ,Statement, ResultSet으로 함
        //이건 전부 interface.
    
        // oracle->mysql DB변경을 하고 싶을 때
        //이론적으로  Class.forName()의 driver와 DriverManger.getConnection만 바꾸면 됨
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("주소", "userId", "password");
            stmt = conn.createStatement();
            rs = stmt.executeQuery("쿼리문");
            // select 쿼리문이라면 rs에 select쿼리 결과들이 저장되어있음

        } catch (SQLException e) {
            //SQLExcetpion은  그냥 익셉션(checkedException) 인데 체크해서 뭐 할게 없어서.
            // 새로운 런타임exception으로 변환해서 던지는게 일반적.
            throw new RuntimeException(e);
        } finally {
            try{
                if(rs!=null ) rs.close();
                if(stmt!=null ) stmt.close();
                if(conn!=null ) conn.close();
            }catch (SQLException e){
                e.printStackTrace();
                //왜 종료가 안됨?
            }
        }




    }
}
