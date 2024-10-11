package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private Matrix matrix;
    private final String word2 = "0101110011111111";
    private final String word3 = "1101110011111110";
    private final String word9 = "0100101001010101";
    private final String addressColumn2 = "0101110011111111";
    private final String addressColumn3 = "1101110011111110";
    private final String addressColumn9 = "0100101001010101";

    @Test
    void getValue() {
        matrix = new Matrix();
        matrix.setValue(5, 9, 1);
        matrix.setValue(16, 16, 1);
        assertEquals(matrix.getValue(5, 9), 1);
        assertEquals(matrix.getValue(0, 0), 1);
    }


    @Test
    void getWord() {
        matrix = new Matrix();
        matrix.setWord(2, word2);
        assertEquals(matrix.getWord(2), word2);
    }

    @Test
    void getAddressColumn() {
        matrix = new Matrix();
        matrix.setAddressColumn(9, addressColumn9);
        assertEquals(matrix.getAddressColumn(9), addressColumn9);
    }

    @Test
    void wordOperation() {
        matrix = new Matrix();
        matrix.setWord(2, word2);
        matrix.setWord(3, word3);
        matrix.wordOperation(2, 3, 4, Operation.PIERCE_OPERATION);
        matrix.wordOperation(2,3,5, Operation.DENIAL_OPERATION);
        matrix.wordOperation(2,3,6, Operation.DISJUNCTION);
        matrix.wordOperation(2,3,7, Operation.IMPLICATION_FIRST_TO_SECOND);
        assertEquals("0010001100000000", matrix.getWord(4));
        assertEquals("0000000000000001", matrix.getWord(5));
        assertEquals("1101110011111111", matrix.getWord(6));
        assertEquals("1111111111111110", matrix.getWord(7));
    }

    @Test
    void addressColumnOperation() {
        matrix = new Matrix();
        matrix.setAddressColumn(2, addressColumn2);
        matrix.setAddressColumn(3, addressColumn3);
        matrix.addressColumnOperation(2, 3, 4, Operation.PIERCE_OPERATION);
        matrix.addressColumnOperation(2,3,5, Operation.DENIAL_OPERATION);
        matrix.addressColumnOperation(2,3,6, Operation.DISJUNCTION);
        matrix.addressColumnOperation(2,3,7, Operation.IMPLICATION_FIRST_TO_SECOND);
        assertEquals("0010001100000000", matrix.getAddressColumn(4));
        assertEquals("0000000000000001", matrix.getAddressColumn(5));
        assertEquals("1101110011111111", matrix.getAddressColumn(6));
        assertEquals("1111111111111110", matrix.getAddressColumn(7));
    }

    @Test
    void arithmeticOperations() {
        matrix = new Matrix();
        matrix.setWord(2, word2);
        matrix.setWord(3, word3);
        matrix.setWord(9, word9);

        matrix.arithmeticOperations("010");
        String result1 = "0101110011110101";
        String result2 = "0100101001000111";

        assertEquals(result1, matrix.getWord(2));
        assertEquals(result2, matrix.getWord(9));
    }

    @Test
    void getWordsInRange() {
        matrix = new Matrix();
        matrix.setWord(2, word2);
        matrix.setWord(3, word3);
        matrix.setWord(9, word9);

        String a = "0100101011010101";
        String b = "1101110011111110";
        List<String> wordsInRange =  matrix.getWordsInRange(a, b);

        assertTrue(wordsInRange.contains(word2));
        assertTrue(wordsInRange.contains(word3));
    }
}