package 스트림연습문제;

import java.util.*;
import java.util.stream.Collectors;


public class 베스트앨범 {
    public static void main(String[] args) {
        String[] arr={"classic", "pop", "classic", "classic", "pop"};
        int[] arr2={500, 600, 150, 800, 2500};
        System.out.println(solution(arr,arr2));
    }

    public static List<Integer> solution(String[] genres, int[] plays) {
        List<Genere> list = new ArrayList<>();
        for (int i = 0; i < genres.length; i++) {
            list.add(new Genere(genres[i], plays[i], i));
        }

        Map<String, List<Genere>> map = list.stream().collect(Collectors.groupingBy(genere -> {
            return genere.genere;
        }));
        List<List<Genere>> listByGenre=new ArrayList<>(map.values());

        List<Integer> list1 = listByGenre.stream()
                .sorted((o1, o2) -> //장르별 합이 높은순으로 장르 정렬
                        o2.stream().mapToInt(genre -> genre.play).sum() -   o1.stream().mapToInt(genre -> genre.play).sum())
                .flatMap( generes -> //장르별로 정렬한 후 상위2개만
                        generes.stream().sorted((o1, o2) -> o2.play - o1.play).map(genere -> genere.index).limit(2))
                .collect(Collectors.toList());
        return list1;
    }

    static class Genere {
        public Genere(String genere, int play, int index) {
            this.genere = genere;
            this.play = play;
            this.index = index;
        }

        String genere;
        int play;
        int index;
    }
}