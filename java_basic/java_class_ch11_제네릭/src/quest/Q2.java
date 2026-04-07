package quest;

import java.util.Arrays;

public class Q2 {
    static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        String[] names = {"Kim", "Lee", "Park"};
        swap(names, 0, 2);
        System.out.println(Arrays.toString(names));
    }
}
