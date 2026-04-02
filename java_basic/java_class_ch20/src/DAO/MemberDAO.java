package DAO;

import DTO.MemberDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberDAO {
    //DAO : Data Access Object
    //CRUD : Create, Read, Update , Delete

    public MemberDAO() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            new RuntimeException(e);
        }
    }

    private void allClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void allSetting(PreparedStatement pstmt, MemberDTO member) throws SQLException {
        pstmt.setString(1, member.getMemId());
        pstmt.setString(2, member.getMemPass());
        pstmt.setString(3, member.getMemName());
        pstmt.setString(4, member.getMemBir());
        pstmt.setString(5, member.getMemZip());
        pstmt.setString(6, member.getMemAdd1());
        pstmt.setString(7, member.getMemAdd2());
        pstmt.setString(8, member.getMemHp());
        pstmt.setString(9, member.getMemMail());
        pstmt.setString(10, member.getMemJob());
        pstmt.setString(11, member.getMemHobby());
    }

    private void allSettingUpdate(PreparedStatement pstmt, MemberDTO member) throws SQLException{
        pstmt.setString(1, member.getMemPass());
        pstmt.setString(2, member.getMemName());
        pstmt.setString(3, member.getMemBir());
        pstmt.setString(4, member.getMemZip());
        pstmt.setString(5, member.getMemAdd1());
        pstmt.setString(6, member.getMemAdd2());
        pstmt.setString(7, member.getMemHp());
        pstmt.setString(8, member.getMemMail());
        pstmt.setString(9, member.getMemJob());
        pstmt.setString(10, member.getMemHobby());
        pstmt.setString(11, member.getMemId());
    }

    private MemberDTO allSettingFromDB(ResultSet rs) throws SQLException{
        MemberDTO member = new MemberDTO();
        member.setMemId(rs.getString("MEM_ID"));
        member.setMemPass(rs.getString("MEM_PASS"));
        member.setMemName(rs.getString("MEM_NAME"));
        member.setMemBir(rs.getString("MEM_BIR"));
        member.setMemZip(rs.getString("MEM_ZIP"));
        member.setMemAdd1(rs.getString("MEM_ADD1"));
        member.setMemAdd2(rs.getString("MEM_ADD2"));
        member.setMemHp(rs.getString("MEM_HP"));
        member.setMemMail(rs.getString("MEM_MAIL"));
        member.setMemJob(rs.getString("MEM_JOB"));
        member.setMemHobby(rs.getString("MEM_HOBBY"));
        member.setMemDelYn(rs.getString("MEM_DEL_YN"));
        return  member;
    }

    public int insertMember(MemberDTO member) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe", "std225", "oracle21c");  //2.연결
            String sql = """
                     INSERT INTO MEMBER (
                          mem_id     ,     mem_pass     ,    mem_name 
                        , mem_bir    ,     mem_zip      ,    mem_add1
                        , mem_add2   ,     mem_hp       ,    mem_mail
                        , mem_job    ,     mem_hobby    ,    mem_del_yn
                        ) 
                     VALUES (
                               ?     ,     ?            ,    ? 
                        ,      ?     ,     ?            ,    ? 
                        ,      ?     ,     ?            ,    ? 
                        ,      ?     ,     ?            ,    'N' 
                       )
                    """;
            pstmt = conn.prepareStatement(sql);
            allSetting(pstmt, member);
            int updateCount = pstmt.executeUpdate();
            return updateCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allClose(rs, pstmt, conn);
        }
    }

    public List<MemberDTO> selectMemberAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe", "std225", "oracle21c");  //2.연결
            String sql = """
                    SELECT 
                     MEM_ID   , MEM_PASS  , MEM_NAME   ,
                     MEM_BIR  , MEM_ZIP   , MEM_ADD1   , 
                     MEM_ADD2 , MEM_HP    , MEM_MAIL   ,
                     MEM_JOB  , MEM_HOBBY , MEM_DEL_YN
                     FROM MEMBER
                     WHERE mem_del_yn='N'
                    """;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<MemberDTO> memberList = new ArrayList<>();
            while (rs.next()) {
                MemberDTO member=allSettingFromDB(rs);
                memberList.add(member);
            }
            return  memberList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allClose(rs, pstmt, conn);
        }
    }

    public MemberDTO selectMemberOne(String memId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe", "std225", "oracle21c");  //2.연결
            String sql = """
                    SELECT 
                     MEM_ID   , MEM_PASS  , MEM_NAME   ,
                     MEM_BIR  , MEM_ZIP   , MEM_ADD1   , 
                     MEM_ADD2 , MEM_HP    , MEM_MAIL   ,
                     MEM_JOB  , MEM_HOBBY , MEM_DEL_YN
                     FROM MEMBER
                     WHERE MEM_ID=?
                     AND mem_del_yn='N'
                    """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,memId);
            rs = pstmt.executeQuery();
            MemberDTO member=null;
            while (rs.next()) {
                 member=allSettingFromDB(rs);
            }
            return member;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allClose(rs, pstmt, conn);
        }
    }

    public int updateMember(MemberDTO member) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe", "std225", "oracle21c");  //2.연결
            String sql= """
                    UPDATE MEMBER SET
                    MEM_PASS=?  , MEM_NAME=?   , MEM_BIR=?  ,
                    MEM_ZIP=?   , MEM_ADD1=?   , MEM_ADD2=? , 
                    MEM_HP=?    , MEM_MAIL=?   , MEM_JOB=?  , 
                    MEM_HOBBY=? 
                    WHERE MEM_ID=?
                    """;
            pstmt=conn.prepareStatement(sql);
            allSettingUpdate(pstmt,member);
            int i= pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allClose(rs, pstmt, conn);
        }
    }

    public int deleteMember(String memId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@nextit.or.kr:1521:xe", "std225", "oracle21c");  //2.연결
            String sql= """
                    UPDATE member SET 
                    mem_del_yn='Y'    
                    WHERE mem_id=?
                    """;
               //다른column도 필요하면 update (ex.마일리지,레벨 등등)
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,memId);
            int i=pstmt.executeUpdate();
            return i;
        } catch (SQLException e) {
           throw new RuntimeException(e);
        } finally {
            allClose(rs, pstmt, conn);
        }
    }


}
