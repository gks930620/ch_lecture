public class P3오버플로우 {
    public static void main(String[] args) {
        int max= 2147483647;
        int max2= Integer.MAX_VALUE;
        System.out.println(max+1);   //직접 쓴 최댓값도 넘치면 음수로 wrap-around
        System.out.println(max2+1);
    }
}
