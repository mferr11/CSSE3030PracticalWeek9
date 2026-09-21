package dispatch;

public class ShippingFeeWithCalculator {
    public int compute(FeeCalculator calc, boolean express, boolean fragile,
                        boolean international) {
        int fee = 5;
        if (express) {
            fee += 10;
        }
        if (fragile) {
            fee = calc.applyFragileFee(fee);
        }
        if (international) {
            fee += 15;
        }
        return fee;
    }
}
