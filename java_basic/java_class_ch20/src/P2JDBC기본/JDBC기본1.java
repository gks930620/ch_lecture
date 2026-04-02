package P2JDBC기본;

import java.sql.*;

public class JDBC기본1 {
    public static void main(String[] args) {
        //1. 드라이버 로드
        //2.  연결
        //3. 쿼리실행 및 데이터 처리
        //4. 연결 종료
        
        
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");  //1.드라이버로드
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            conn= DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe","std225","oracle21c");  //2.연결

            //3.쿼리실행및 데이터처리
            stmt = conn.createStatement();  //이 stmt는 위의 주소에서 쿼리 실행하는 객체
            rs=stmt.executeQuery(" SELECT 7 FROM dual  ");
            if(rs.next()){
                System.out.println(rs.getInt(1));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
