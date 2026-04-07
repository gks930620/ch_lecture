package p7;

public class AppleMerchant {
    private int applePrice;

    public int getApplePrice() {
        return applePrice;
    }
    public void setApplePrice(int applePrice) {
        if(applePrice<0){
            System.out.println("가격이 음수입니다. 다시 설정하세요");
        }else{
            this.applePrice = applePrice;
        }
    }
}
