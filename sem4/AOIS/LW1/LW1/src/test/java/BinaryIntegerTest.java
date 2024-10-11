import org.example.BinaryInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryIntegerTest {


    @Test
    public void testDecimalToBinary() {
        BinaryInteger binaryInteger1 = new BinaryInteger(5);
        BinaryInteger binaryInteger2 = new BinaryInteger(-5);
        BinaryInteger binaryInteger3 = new BinaryInteger(24);
        BinaryInteger binaryInteger4 = new BinaryInteger(0);
        assertEquals("00000101", binaryInteger1.toString());
        assertEquals("10000101", binaryInteger2.toString());
        assertEquals("00011000", binaryInteger3.toString());
        assertEquals("00000000", binaryInteger4.toString());
    }

    @Test
    public void testBinaryToDecimal() {
        BinaryInteger binaryInteger1 = new BinaryInteger(5);
        BinaryInteger binaryInteger2 = new BinaryInteger(-5);
        BinaryInteger binaryInteger3 = new BinaryInteger(120);
        assertEquals(5, binaryInteger1.binaryToInt());
        assertEquals(-5, binaryInteger2.binaryToInt());
        assertEquals(120, binaryInteger3.binaryToInt());
    }


    @Test
    public void testAddition(){
        BinaryInteger binaryInteger1 = new BinaryInteger(5);
        BinaryInteger binaryInteger2 = new BinaryInteger(10);
        BinaryInteger binaryInteger3 = new BinaryInteger(-21);
        assertEquals(15, binaryInteger1.additionWithReverseCode(binaryInteger2).binaryToInt());
        assertEquals(-11, binaryInteger3.additionWithAdditionalCode(binaryInteger2).binaryToInt());
    }
    @Test
    public void testMultiplication(){
        BinaryInteger binaryInteger1 = new BinaryInteger(5);
        BinaryInteger binaryInteger2 = new BinaryInteger(10);
        BinaryInteger binaryInteger3 = new BinaryInteger(-21);
        assertEquals(50, binaryInteger1.multiplication(binaryInteger2).binaryToInt());
        assertEquals(-210, binaryInteger3.multiplication(binaryInteger2).binaryToInt());
    }
    @Test
    public void testDifference(){
        BinaryInteger binaryInteger1 = new BinaryInteger(5);
        BinaryInteger binaryInteger2 = new BinaryInteger(10);
        BinaryInteger binaryInteger3 = new BinaryInteger(-21);
       assertEquals(-5, binaryInteger1.differenceWithReverseCode(binaryInteger2).binaryToInt());
        assertEquals(31, binaryInteger2.differenceWithAdditionalCode(binaryInteger2, binaryInteger3).binaryToInt());
    }

    @Test
    public void testDivision(){
        BinaryInteger binaryInteger1 = new BinaryInteger(25);
        BinaryInteger binaryInteger2 = new BinaryInteger(4);
        BinaryInteger binaryInteger3 = new BinaryInteger(-21);
        assertEquals(6.25f,  binaryInteger1.division(binaryInteger1, binaryInteger2).toDecimal(), 0.001);
        assertEquals(-0.1904, binaryInteger2.division(binaryInteger2, binaryInteger3).toDecimal(), 0.001);
    }

}
