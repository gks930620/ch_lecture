import java.util.Arrays;

public class P2Null과NullpointerException2 {
    public static void main(String[] args) {
        int[] intArrayNormal=new int[]{0,1,2,3,4};
        int[] intArrayNotNull=new int[0];
        int[] intArrayNull=null;

        System.out.println(intArrayNormal[1]);
//        Sycstem.out.println(intArrayNotNull[0]);   //0인 배열과 null은 다르다. ArraysOutof
//        System.out.println(intArrayNull[0]); //이건 nullPointerExeption


    }
}
