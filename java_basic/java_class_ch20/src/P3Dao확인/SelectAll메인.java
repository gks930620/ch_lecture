package P3Dao확인;

import DAO.MemberDAO;
import DTO.MemberDTO;

import java.util.List;
import java.util.TreeSet;

public class SelectAll메인 {
    public static void main(String[] args) {
        MemberDAO memberDAO=new MemberDAO();
        List<MemberDTO> memberList = memberDAO.selectMemberAll();
        memberList.forEach(memberDTO -> System.out.println(memberDTO));


    }
}
