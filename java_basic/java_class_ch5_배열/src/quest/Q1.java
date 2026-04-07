package quest;

public class Q1 {
    public static void main(String[] args) {
        int[] nums = {7, 2, 19, 4, 11, 3};
        int max = nums[0];
        int min = nums[0];

        for (int n : nums) {
            if (n > max) {
                max = n;
            }
            if (n < min) {
                min = n;
            }
        }

        System.out.println("최댓값: " + max);
        System.out.println("최솟값: " + min);
    }
}
