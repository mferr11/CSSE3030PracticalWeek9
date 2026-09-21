package inline;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShippingFeeTest {

    private final ShippingFee shippingFee = new ShippingFee();

    // t1 = (express=true, fragile=false, international=false)
    @Test
    public void testT1() {
        assertEquals(15, shippingFee.compute(true, false, false));
    }

    // t2 = (express=false, fragile=true, international=false)
    @Test
    public void testT2() {
        assertEquals(8, shippingFee.compute(false, true, false));
    }

    // t3 = (express=false, fragile=false, international=true)
    @Test
    public void testT3() {
        assertEquals(20, shippingFee.compute(false, false, true));
    }

    // t4 = (express=true, fragile=true, international=true)
    @Test
    public void testT4() {
        assertEquals(33, shippingFee.compute(true, true, true));
    }
}
