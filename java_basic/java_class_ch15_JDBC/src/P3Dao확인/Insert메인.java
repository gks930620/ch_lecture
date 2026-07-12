package P3Dao확인;

import DAO.MemberDAO;
import DTO.MemberDTO;

public class Insert메인 {
    public static void main(String[] args) {
        MemberDAO memberDAO = new MemberDAO();
        MemberDTO member = new MemberDTO();
        member.setMemId("user01");   //mem_id는 PK라서 같은 값으로 두 번 실행하면 unique constraint 오류. 재실행 시 값 변경.
        member.setMemPass("1004");   //나중에 삭제할 때 귀찮으니까 다 1004로 통일.
        member.setMemName("홍길동");
        member.setMemBir("2024-06-20");  //이거는 형식에 맞게...
        member.setMemZip("34022");
        member.setMemAdd1("예시아파트");
        member.setMemAdd2("101호");
        member.setMemHp("010-0000-0000");
        member.setMemMail("hong@example.com");
        member.setMemJob("JB01");  //그냥 JB00  형식으로만
        member.setMemHobby("HB01");   //HB00형식으로만
        memberDAO.insertMember(member);


    }
}
