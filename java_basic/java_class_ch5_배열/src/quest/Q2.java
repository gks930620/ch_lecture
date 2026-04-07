package quest;

public class Q2 {
    public static void main(String[] args) {
        int[] scores = {80, 95, 70, 100, 88};
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        double avg = (double) sum / scores.length;
        System.out.println("평균: " + avg);
    }
}
