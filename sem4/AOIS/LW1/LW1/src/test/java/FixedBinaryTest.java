import org.example.FixedBinary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedBinaryTest {
    @Test
    public void testBinaryToDecimal(){
        FixedBinary fixedBinary1 = new FixedBinary("0000", "0000", 0);
        FixedBinary fixedBinary2 = new FixedBinary("10001", "11", 1);
        assertEquals(0.0f, fixedBinary1.toDecimal());
        assertEquals(-17.75f, fixedBinary2.toDecimal());
    }
}
