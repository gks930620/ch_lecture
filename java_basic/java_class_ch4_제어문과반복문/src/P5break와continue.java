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
        System.out.println("-----------------");
        //라벨 break: 중첩 반복문에서 안쪽 break는 안쪽 루프만 끝냄.
        //바깥 루프까지 한번에 끝내고 싶으면 라벨을 붙이고 break 라벨명;
        outer:
        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<5 ; j++){
                if(i*j>6){
                    break outer;   //안쪽+바깥 루프 모두 종료
                }
                System.out.println(i + " * " + j + " = " + (i*j));
            }
        }
        //강력하지만 남용하면 흐름 따라가기 어려우니까 신중하게 쓰자

    }
}
