package P2JDBC기본;

import DTO.MemberDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JDBC기본2Select에서매핑DTO2 {
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
                      +" FROM member   ";
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            List<MemberDTO> memberList=new ArrayList<>();
            while ( rs.next()){
                MemberDTO member=new MemberDTO();
                member.setMemId(rs.getString("mem_id"));
                member.setMemPass(rs.getString("mem_pass"));
                member.setMemName(rs.getString("mem_name"));
                member.setMemBir(rs.getString("mem_bir"));
                member.setMemZip(rs.getString("mem_zip"));
                memberList.add(member);
            }

            System.out.println(memberList);

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
