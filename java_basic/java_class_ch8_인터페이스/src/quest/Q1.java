package quest;

public class Q1 {
    interface Playable {
        void play();
    }

    static class MusicPlayer implements Playable {
        @Override
        public void play() {
            System.out.println("음악 재생 중");
        }
    }

    public static void main(String[] args) {
        Playable player = new MusicPlayer();
        player.play();
    }
}
