package P2JDBC기본;

import DTO.MemberDTO;

import java.sql.*;
import java.util.Scanner;

public class JDBC기본2Select에서매핑DTO {
    public static void main(String[] args) {
        //  드라이버로드 - 연결 - 쿼리실행 및 처리 - 종료
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException e){
            new RuntimeException(e);
        }

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Scanner scanner=new Scanner(System.in);
        String memId=scanner.nextLine();
        try{
            conn= DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe","std225","oracle21c");  //2.연결
            String sql=" SELECT  mem_id, mem_pass, mem_name, mem_bir ,mem_zip  "
                      +" FROM member  where mem_id= ?  ";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, memId);
            rs=pstmt.executeQuery();
            MemberDTO member=new MemberDTO();
            while ( rs.next()){
                member.setMemId(rs.getString("mem_id"));
                member.setMemPass(rs.getString("mem_pass"));
                member.setMemName(rs.getString("mem_name"));
                member.setMemBir(rs.getString("mem_bir"));
                member.setMemZip(rs.getString("mem_zip"));
            }
            System.out.println(member);

        }catch (SQLException e){
            new RuntimeException(e);
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
