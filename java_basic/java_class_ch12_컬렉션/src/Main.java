import java.util.*;

public class Main {
    public static void main(String[] args) {
        String[] arr={"classic", "pop", "classic", "classic", "pop"};
        int[] arr2={500, 600, 150, 800, 2500};
        System.out.println(solution(arr,arr2));

    }


    public static List<Integer> solution(String[] genres, int[] plays) {
        //장르명 -> 그 장르의 곡 목록 으로 그룹핑 (키가 없으면 새 리스트를 만들어서 넣어줌)
        Map<String, List<Genre>> map = new HashMap<>();
        for(int i=0 ; i<genres.length ; i++){
            map.computeIfAbsent(genres[i], key -> new ArrayList<>())
                    .add(new Genre(genres[i], plays[i], i));
        }

        List<List<Genre>> listByGenre = new ArrayList<>(map.values());

        listByGenre.sort((o1, o2) -> Integer.compare(sumPlays(o2), sumPlays(o1)));
        //장르별 플레이합이 높은 장르순 정렬  (pop이 클래식보다 높으니까 pop이 먼저 나오게)
        //뺄셈(sum2-sum1) 비교는 오버플로 위험이 있어서 Integer.compare 사용

        for (List<Genre> genreList : listByGenre) {
            genreList.sort((o1, o2) -> Integer.compare(o2.play, o1.play));
        }
        //각 장르별로 플레이가 높은 순으로 정렬   (pop에서 2500,600순.   classic에서 800 500 150순)



        List<Integer> answer=new ArrayList<>();
        for (List<Genre> genreList : listByGenre) {
            answer.add(genreList.get(0).index);
            if(genreList.size()>=2){
                answer.add(genreList.get(1).index);
            }
        }

        return answer;
    }

    //장르 곡 목록의 플레이 수 합계
    private static int sumPlays(List<Genre> genreList) {
        int sum = 0;
        for (Genre genre : genreList) {
            sum += genre.play;
        }
        return sum;
    }

    static class Genre {
        public Genre(String genre, int play, int index) {
            this.genre = genre;
            this.play = play;
            this.index=index;
        }

        String genre;
        int play;
        int index;


    }
}
