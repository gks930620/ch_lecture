package P3Dao확인;

import DAO.MemberDAO;

public class Delete메인 {
    public static void main(String[] args) {
        //했으면 Free도 만들어서 ㄱㄱ해보기 
        MemberDAO memberDAO=new MemberDAO();
        String memId="a009";
        memberDAO.deleteMember(memId);
    }
}
