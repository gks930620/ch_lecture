public class Main {
    public static void main(String[] args)
    {


        System.out.println("answer : "+solution(8,4,new int[]{2,3,4,5,6}));
        System.out.println("기댓값 :2" );
    }

    public static int solution(int n, int m, int[] section) { //오름차순임.
        int count=0;
        for(int i= 0 ; i<section.length ;  ){
            int start=section[i];
            count++;
            while(  i<section.length && start+m > section[i] ){
                i++;
            }
        }
        return count;
    }

}