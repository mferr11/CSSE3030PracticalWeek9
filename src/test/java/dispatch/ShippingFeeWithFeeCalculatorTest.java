package dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShippingFeeWithFeeCalculatorTest {

    // t2 = (express=false, fragile=true, international=false) with a plain FeeCalculator
    @Test
    public void testT2() {
        ShippingFeeWithCalculator shippingFee = new ShippingFeeWithCalculator();
        FeeCalculator calc = new FeeCalculator();
        assertEquals(8, shippingFee.compute(calc, false, true, false));
    }
}
