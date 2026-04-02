package P2JDBC기본;

import java.sql.*;
import java.util.Scanner;

public class JDBC기본2SelectWhere2Pstmt {
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
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        Scanner scanner=new Scanner(System.in);
        String memId=scanner.nextLine();
        try {
            conn= DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe","std225","oracle21c");  //2.연결
            //3.쿼리실행및 데이터처리
            String sql= " SELECT mem_id, mem_pass, mem_name, mem_bir "
                     +  "  FROM member   WHERE mem_id=  ?  ";
            //  ''처리하기힘드니까 stmt 대신 pstmt
            // 그리고 DB에서 쿼리 캐시때문에 pstmt가 성능이 더 좋음.  
            // select * from member where mem_id='a009' , select * from member where mem_id='a008' 은 서로 다른 쿼리
            // select * from member where mem_id=? 는 한개만 있으면 됨.

            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,memId);   //첫번째 ?에  memId값을 세팅함.
            rs=pstmt.executeQuery();


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
                if(pstmt!=null) pstmt.close();
                if(conn!=null) conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


    }
}
