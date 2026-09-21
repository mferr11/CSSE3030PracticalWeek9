package inline;

public class ShippingFee {
    public int compute(boolean express, boolean fragile, boolean international) {
        int fee = 5;
        if (express) {
            fee += 10;
        }
        if (fragile) {
            fee += 3;
        }
        if (international) {
            fee += 15;
        }
        return fee;
    }
}
