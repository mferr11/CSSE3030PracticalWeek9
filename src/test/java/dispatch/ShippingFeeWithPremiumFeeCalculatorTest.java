package dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShippingFeeWithPremiumFeeCalculatorTest {

    // t2 = (express=false, fragile=true, international=false) with a PremiumFeeCalculator
    @Test
    public void testT2() {
        ShippingFeeWithCalculator shippingFee = new ShippingFeeWithCalculator();
        FeeCalculator calc = new PremiumFeeCalculator();
        assertEquals(11, shippingFee.compute(calc, false, true, false));
    }
}
