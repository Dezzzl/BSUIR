import org.example.BinaryFloat;
import org.example.BinaryInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryFloatTest {
    @Test
    public void testBinToFloat(){
        BinaryFloat binaryInteger1 = new BinaryFloat(27.25f);
        BinaryFloat binaryInteger2 = new BinaryFloat(-5.234f);
        assertEquals(27.25f, binaryInteger1.binaryFloatToDecimal());
        assertEquals(-5.234f, binaryInteger2.binaryFloatToDecimal(), 0.001);
    }

    @Test
    public void testAddition(){
        BinaryFloat binaryInteger1 = new BinaryFloat(27.25f);
        BinaryFloat binaryInteger2 = new BinaryFloat(5.234f);
        BinaryFloat binaryInteger3 = new BinaryFloat(-9.76f);
        BinaryFloat binaryInteger4 = new BinaryFloat(-4.24f);
        BinaryFloat binaryInteger5 = new BinaryFloat(-27.325f);
        BinaryFloat binaryInteger6 = new BinaryFloat(-0.125f);

        assertEquals(-14.0f, binaryInteger3.sum(binaryInteger3, binaryInteger4).binaryFloatToDecimal(), 0.001);
        assertEquals(32.484f, binaryInteger1.sum(binaryInteger1, binaryInteger2).binaryFloatToDecimal(), 0.001);
        assertEquals(17.49f, binaryInteger1.sum(binaryInteger1, binaryInteger3).binaryFloatToDecimal(), 0.001);
        assertEquals(-27.450f, binaryInteger1.sum(binaryInteger5, binaryInteger6).binaryFloatToDecimal(), 0.001);
    }
}
