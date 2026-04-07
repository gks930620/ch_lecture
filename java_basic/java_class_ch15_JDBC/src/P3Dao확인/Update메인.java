package P3Dao확인;

import DAO.MemberDAO;
import DTO.MemberDTO;

public class Update메인 {
    public static void main(String[] args) {
        MemberDAO memberDAO=new MemberDAO();
        MemberDTO member=new MemberDTO();
        member.setMemId("a009"); //a008
        member.setMemPass("1004");   //나중에 삭제할 때 귀찮으니까 다 1004로 통일.
        member.setMemName("김민지");
        member.setMemBir("1993-06-20");  //이거는 형식에 맞게...
        member.setMemZip("11111");
        member.setMemAdd1("우와우우");
        member.setMemAdd2("904-903");
        member.setMemHp("000-000-0000");
        member.setMemMail("gks930620@gmail.com");
        member.setMemJob("JB01");  //그냥 JB00  형식으로만
        member.setMemHobby("HB01");   //HB00형식으로만

        memberDAO.updateMember(member);

    }
}
