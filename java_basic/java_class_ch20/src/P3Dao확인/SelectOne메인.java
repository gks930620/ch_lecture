package P3Dao확인;

import DAO.MemberDAO;
import DTO.MemberDTO;

public class SelectOne메인 {
    public static void main(String[] args) {
        MemberDAO memberDAO=new MemberDAO();
        MemberDTO member = memberDAO.selectMemberOne("gks930620");
        System.out.println(member);

    }
}
