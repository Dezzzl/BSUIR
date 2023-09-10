package main.java.com.dezzzl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PolynomialTest {

    private Polynomial polynomial1;
    private Polynomial polynomial2;

    private Polynomial polynomial3;

    private Polynomial polynomial4;

    private Polynomial polynomial5;

    private Polynomial polynomial6;

    @Before
    public void setUp() {
        polynomial1 = new Polynomial(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)), 5);
        polynomial2 = new Polynomial(new ArrayList<>(Arrays.asList(5.0, 6.0, 7.0, 8.0)), 3);
        polynomial3 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 10.0, 0.0, 3.0)), 3);
        polynomial4 = new Polynomial(new ArrayList<>(Arrays.asList(13.0, 47.0, 34.0, 23.0, 25.0, 59.0, 81.0, 1.0)), 7);
        polynomial5 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 2.0, 3.0, 4.0, 5.0, 6.0)), 5);
        polynomial6 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 30.0, 45.0)), 4);
    }

    @Test
    public void testToString() {
        String expectedResult1 = "6.0x^5 + 5.0x^4 + 4.0x^3 + 3.0x^2 + 2.0x + 1.0";
        String expectedResult2 = "6.0x^5 + 5.0x^4 + 4.0x^3 + 3.0x^2 + 2.0x";
        String expectedResult3 = "3.0x^3 + 10.0x";
        String result1 = polynomial1.toString();
        String result2 = polynomial5.toString();
        String result3 = polynomial3.toString();
        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
        assertEquals(expectedResult3, result3);
    }

    @Test
    public void testAddition() {
        Polynomial expectedResult1 = new Polynomial(new ArrayList<>(Arrays.asList(6.0, 8.0, 10.0, 12.0, 5.0, 6.0)), 5);
        Polynomial expectedResult2 = new Polynomial(new ArrayList<>(Arrays.asList(13.0, 57.0, 34.0, 26.0, 25.0, 59.0, 81.0, 1.0)), 7);
        Polynomial expectedResult3 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 10.0, 0.0, 33.0, 45.0)), 4);
        Polynomial result1 = polynomial1.addition(polynomial2);
        Polynomial result2 = polynomial3.addition(polynomial4);
        Polynomial result3 = polynomial6.addition(polynomial3);
        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
        assertEquals(expectedResult3, result3);
    }

    @Test
    public void testSubtraction() {
        Polynomial expectedResult1 = new Polynomial(new ArrayList<>(Arrays.asList(-4.0, -4.0, -4.0, -4.0, 5.0, 6.0)), 5);
        Polynomial expectedResult2 = new Polynomial(new ArrayList<>(Arrays.asList(4.0, 4.0, 4.0, 4.0, -5.0, -6.0)), 5);
        Polynomial expectedResult3 = new Polynomial(new ArrayList<>(List.of(0.0)), 0);
        Polynomial result1 = polynomial1.subtract(polynomial2);
        Polynomial result2 = polynomial2.subtract(polynomial1);
        Polynomial result3 = polynomial1.subtract(polynomial1);
        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
        assertEquals(expectedResult3, result3);
    }

    @Test
    public void testMultiplication() {
        Polynomial expectedResult1 = new Polynomial(new ArrayList<>(Arrays.asList(5.0, 16.0, 34.0, 60.0, 86.0, 112.0, 103.0, 82.0, 48.0)), 8);
        Polynomial expectedResult2 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 130.0, 470.0, 379.0, 371.0, 352.0, 659.0, 885.0, 187.0, 243.0, 3.0)), 10);
        Polynomial expectedResult3 = new Polynomial(new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 300.0, 450.0, 90.0, 135.0)), 7);
        Polynomial result1 = polynomial1.multiply(polynomial2);
        Polynomial result2 = polynomial3.multiply(polynomial4);
        Polynomial result3 = polynomial6.multiply(polynomial3);
        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult2, result2);
        assertEquals(expectedResult3, result3);
    }

    @Test
    public void testDivision() {
        List<Polynomial> results1 = polynomial1.divide(polynomial2);
        List<Polynomial> expectedResults1 = new ArrayList<>(Arrays.asList(
                new Polynomial(new ArrayList<>(Arrays.asList(1.1757, 2.3671, -0.3164)), 2),
                new Polynomial(new ArrayList<>(Arrays.asList(-0.0351, -0.0312, 0.7500)), 2)
        ));
        for (int i = 0; i < expectedResults1.size(); i++) {
            Polynomial expected = expectedResults1.get(i);
            Polynomial actual = results1.get(i);
            assertEquals(expected.getDegree(), actual.getDegree());
            for (int j = 0; j <= expected.getDegree(); j++) {
                assertEquals(expected.getCoefficient(j), actual.getCoefficient(j), 0.001);
            }
        }

        List<Polynomial> results2 = polynomial4.divide(polynomial3);
        List<Polynomial> expectedResults2 = new ArrayList<>(Arrays.asList(
                new Polynomial(new ArrayList<>(Arrays.asList(13.0000, 588.8519, 850.6667)), 2),
                new Polynomial(new ArrayList<>(Arrays.asList(-54.1852, -81.6667, 18.5556, 27.0000, 0.3333)), 4)
        ));
        for (int i = 0; i < expectedResults2.size(); i++) {
            Polynomial expected = expectedResults2.get(i);
            Polynomial actual = results2.get(i);
            assertEquals(expected.getDegree(), actual.getDegree());
            for (int j = 0; j <= expected.getDegree(); j++) {
                assertEquals(expected.getCoefficient(j), actual.getCoefficient(j), 0.001);
            }
        }

        List<Polynomial> results3 = polynomial1.divide(polynomial1);
        List<Polynomial> expectedResults3 = new ArrayList<>(Arrays.asList(
                new Polynomial(new ArrayList<>(Arrays.asList(0.0)), 0),
                new Polynomial(new ArrayList<>(Arrays.asList(1.0)), 0)
        ));
        for (int i = 0; i < expectedResults3.size(); i++) {
            Polynomial expected = expectedResults3.get(i);
            Polynomial actual = results3.get(i);
            assertEquals(expected.getDegree(), actual.getDegree());
            for (int j = 0; j <= expected.getDegree(); j++) {
                assertEquals(expected.getCoefficient(j), actual.getCoefficient(j), 0.001);
            }
        }
    }


    @Test
    public void testCalculateTheValue() {
        double argument1 = 2.0;
        double expectedResult1 = 321.0;
        double result1 = polynomial1.calculateTheValue(argument1);
        assertEquals(expectedResult1, result1, 0.001);
        double argument2 = 0.0;
        double expectedResult2 = 0.0;
        double result2 = polynomial5.calculateTheValue(argument2);
        assertEquals(expectedResult2, result2, 0.001);
        double argument3 = 5.0;
        double expectedResult3 = 1547723.0;
        double result3 = polynomial4.calculateTheValue(argument3);
        assertEquals(expectedResult3, result3, 0.001);
    }

    @Test
    public void testGetCountOfNotZeroCoefficients() {
        int result1 = polynomial1.getCountOfNotZeroCoefficients();
        int result2 = polynomial2.getCountOfNotZeroCoefficients();
        int result3 = polynomial3.getCountOfNotZeroCoefficients();
        assertEquals(6, result1);
        assertEquals(4, result2);
        assertEquals(2, result3);

    }

}