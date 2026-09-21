package dispatch;

public class PremiumFeeCalculator extends FeeCalculator {
    public int applyFragileFee(int fee) {
        return fee + 6;
    }
}
