package quest;

public class Q1 {
    interface Playable {
        void play();
        void stop();
    }

    static class MusicPlayer implements Playable {
        @Override
        public void play() {
            System.out.println("음악 재생 중");
        }

        @Override
        public void stop() {
            System.out.println("음악 정지");
        }
    }

    static class VideoPlayer implements Playable {
        @Override
        public void play() {
            System.out.println("영상 재생 중");
        }

        @Override
        public void stop() {
            System.out.println("영상 정지");
        }
    }

    public static void main(String[] args) {
        Playable player = new MusicPlayer();
        player.play();
        player.stop();

        // 같은 인터페이스를 구현한 VideoPlayer로 교체해도 호출 코드는 동일
        player = new VideoPlayer();
        player.play();
        player.stop();
    }
}
