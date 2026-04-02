package practice;

public class Q3 {
    public static void main(String[] args) {
        double centauriDistanceFromEarth=Math.pow(10,12)*40; //km
        double speedOfLight=300_000;   //   m/s
        speedOfLight=speedOfLight*60*60*24*365;   //  km/year

        System.out.println("센타우리가 별까지 가는데 걸리는 시간은" +centauriDistanceFromEarth/speedOfLight +"년이다");
    }
}
