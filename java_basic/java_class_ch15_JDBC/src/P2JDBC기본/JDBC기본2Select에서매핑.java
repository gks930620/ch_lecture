package P2JDBC기본;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JDBC기본2Select에서매핑 {
    public static void main(String[] args) {
        //  드라이버로드 - 연결 - 쿼리실행 및 처리 - 종료
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Scanner scanner=new Scanner(System.in);
        String memId=scanner.nextLine();
        try{

            conn= DriverManager.getConnection("jdbc:oracle:thin:@DB주소:1521:xe","DB유저ID","DB비밀번호");  //2.연결 (DB주소·유저ID·비밀번호는 본인 환경에 맞게 변경)
            String sql=" SELECT  mem_id, mem_pass, mem_name, mem_bir ,mem_zip  "
                      +" FROM member  where mem_id= ?  ";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, memId);
            rs=pstmt.executeQuery();
            Map<String, Object> map=new HashMap<>();
            while ( rs.next()){
                System.out.print(rs.getString("mem_id"));
                map.put("mem_id" , rs.getString("mem_id"));      //key는 컬럼명, value는 컬럼 값
                map.put("mem_pass" , rs.getString("mem_pass"));
                map.put("mem_name" , rs.getString("mem_name"));
                map.put("mem_bir" , rs.getString("mem_bir"));
                map.put("mem_zip" , rs.getString("mem_zip"));
                //map에다가 담을 수 있지만 보통은 테이블에 대응되는 데이터저장용 클래스를 만듦.
                // 그 저장용 클래스를 DTO(VO)라고 합니다.
            }
            System.out.println(map);

        }catch (SQLException e){
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
