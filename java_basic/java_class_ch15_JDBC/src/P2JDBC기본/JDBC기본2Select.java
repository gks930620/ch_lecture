package P2JDBC기본;

import java.sql.*;

public class JDBC기본2Select {
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
            conn= DriverManager.getConnection("jdbc:oracle:thin:@DB주소:1521:xe","DB유저ID","DB비밀번호");  //2.연결 (DB주소·유저ID·비밀번호는 본인 환경에 맞게 변경)
            //3.쿼리실행및 데이터처리
            stmt = conn.createStatement();  //이 stmt는 위의 주소에서 쿼리 실행하는 객체
            String sql= """
                    SELECT mem_id, mem_pass, mem_name, mem_bir
                    FROM member
                    """;
            rs=stmt.executeQuery(sql);
            while (rs.next()){
                System.out.print(rs.getString("mem_id")   +" ,");
                System.out.print(rs.getString("mem_pass") +" ,");
                System.out.print(rs.getString("mem_name") +" ,");
                System.out.print(rs.getString("mem_bir")  +" ,");
                System.out.println();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if(rs!=null) rs.close();
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
}
