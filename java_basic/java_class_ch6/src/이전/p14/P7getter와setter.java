package 이전.p14;

public class P7getter와setter {
    //getter/setter가 왜 필요한지.
    public static void main(String[] args) {
        AppleMerchant appleMerchant=new AppleMerchant();
        //appleMerchant.applePrice=-1000;
        // 1.외부에서 사과 가격을 음수로 설정하는 사태가 발생할 수 있음
        // 2. 외부에서 이상한 값으로 변하는걸 막기 위해 필드를 private으로 선언함.
        // 3. 그럼 외부에서 필드의 값을 얻거나 변경은 어떻게 함?  => get/set메소드
        appleMerchant.setApplePrice(-1000);
        System.out.println("사과 가격  : " + appleMerchant.getApplePrice() );
        appleMerchant.setApplePrice(1000);
        System.out.println("사과 가격  : " + appleMerchant.getApplePrice() );


    }

}
