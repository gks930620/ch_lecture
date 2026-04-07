public class P5break와continue {
    public static void main(String[] args) {
        //참고로 {}안에 한줄인 경우는 {} 생략가능함. 근데 여러분들은 생략하지마세요
        //연습해야되니까..
        for(int i=0 ; i<10 ; i++){
            if(i==5){
                break;
            }
            System.out.println(i);
        }
        System.out.println("-----------------");
        for(int i=0 ; i<10 ; i++){
            if(i==5){
                continue;
            }
            System.out.println(i);
        }

    }
}
