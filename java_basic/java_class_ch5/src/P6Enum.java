public class P6Enum {
    public static void main(String[] args) {
        //ENUM도 하나의 데이터타입.  heap영역에 있음
        //꽤 자주 씀. 지금하면 괜히 머리 아픈듯. 나중에 혼자 공부해도 충분한 것 가탇.
        Week monday=Week.MONDAY;
        Week monday2=Week.MONDAY;
        System.out.println(monday2==monday);
        System.out.println("monday name 값: " + monday.name() );
        Week[] weeks = Week.values();
        for(Week week : weeks){
            System.out.println(week);
        }
        //이정도만 하자.
    }


}
