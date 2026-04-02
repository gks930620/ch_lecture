import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String[] arr={"classic", "pop", "classic", "classic", "pop"};
        int[] arr2={500, 600, 150, 800, 2500};
        System.out.println(solution(arr,arr2));

    }


    public static List<Integer> solution(String[] genres, int[] plays) {
        List<Genere> list= new ArrayList<>();
        for(int i=0 ; i<genres.length ; i++){
            list.add(new Genere(genres[i],plays[i],i));
        }
        Map<String, List<Genere>> map = list.stream().collect(Collectors.groupingBy(genere -> {
            return genere.genere;
        })); //장르별
        
        List<List<Genere>> listByGenre = new ArrayList<>(map.values());

        listByGenre.sort((o1, o2) -> {
            int sum1 = o1.stream().mapToInt(o -> o.play).sum();
            int sum2 = o2.stream().mapToInt(o -> o.play).sum();
            return sum2-sum1;
        }); //장르별 플레이합이 높은 장르순 정렬  (pop이 클래식보다 높으니까 pop이 먼저 나오게)

        listByGenre.forEach(genereList -> {
            genereList.sort((o1, o2) -> o2.play-o1.play);
        });
        //각 장르별로 플레이가 높은 순으로 정렬   (pop에서 2500,600순.   class에서 800 500 150순



        List<Integer> answer=new ArrayList<>();
        listByGenre.forEach(genereList -> {
            answer.add(genereList.get(0).index);
            if(genereList.size()>=2){
                answer.add(genereList.get(1).index);
            }
        });

        return answer;
    }

    static class Genere {
        public Genere(String genere, int play, int index) {
            this.genere = genere;
            this.play = play;
            this.index=index;
        }

        String genere;
        int play;
        int index;


    }
}