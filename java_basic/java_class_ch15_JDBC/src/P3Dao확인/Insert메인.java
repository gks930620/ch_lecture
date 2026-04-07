package P3Dao확인;

import DAO.MemberDAO;
import DTO.MemberDTO;

public class Insert메인 {
    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAO();
        MemberDTO member = new MemberDTO();
        member.setMemId("gks930620");
        member.setMemPass("1004");   //나중에 삭제할 때 귀찮으니까 다 1004로 통일.
        member.setMemName("한창희");
        member.setMemBir("2024-06-20");  //이거는 형식에 맞게...
        member.setMemZip("34022");
        member.setMemAdd1("범지기마을");
        member.setMemAdd2("904-903");
        member.setMemHp("010-8033-3117");
        member.setMemMail("gks930620@naver.com");
        member.setMemJob("JB01");  //그냥 JB00  형식으로만
        member.setMemHobby("HB01");   //HB00형식으로만
        memberDAO.insertMember(member);


    }
}
