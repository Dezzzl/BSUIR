package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Matrix {

    private final int[][] matrix;

    private static final int SIZE = 16;

    private static final Map<Operation, BiFunction<Boolean, Boolean, Boolean>> OPERATIONS_MAP = new HashMap<>();

    static {
        OPERATIONS_MAP.put(Operation.DENIAL_OPERATION, OperationResolver::denialOperation);
        OPERATIONS_MAP.put(Operation.PIERCE_OPERATION, OperationResolver::pierceOperation);
        OPERATIONS_MAP.put(Operation.IMPLICATION_FIRST_TO_SECOND, OperationResolver::implicationFirstToSecond);
        OPERATIONS_MAP.put(Operation.DISJUNCTION, OperationResolver::disjunction);
    }

    public Matrix() {
        matrix = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    public int getValue(int i, int j) {
        i = i % SIZE;
        j = j % SIZE;
        return this.matrix[i][j];
    }

    public void setValue(int i, int j, int value) {
        i = i % SIZE;
        j = j % SIZE;
        matrix[i][j] = value;
    }

    public String getWord(int columnIndex) {
        StringBuilder word = new StringBuilder();
        for (int i = columnIndex; i < columnIndex + SIZE; i++) {
            word.append(getValue(i, columnIndex));
        }
        return word.toString();
    }

    public void setWord(int columnIndex, String word) {
        for (int i = columnIndex; i < columnIndex + SIZE; i++) {
            setValue(i, columnIndex, Character.getNumericValue(word.charAt(i - columnIndex)));
        }
    }

    public String getAddressColumn(int rowIndex) {
        StringBuilder addressColumn = new StringBuilder();
        for (int i = rowIndex, j = 0; i < rowIndex + SIZE; i++, j++) {
            addressColumn.append(getValue(i, j));
        }
        return addressColumn.toString();
    }

    public void setAddressColumn(int rowIndex, String addressColumn) {
        for (int i = rowIndex, j = 0; i < rowIndex + SIZE; i++, j++) {
            setValue(i, j, Character.getNumericValue(addressColumn.charAt(j)));
        }
    }

    public void wordOperation(int columnIndex1, int columnIndex2, int columnIndex3, Operation operation) {
        String firstWord = getWord(columnIndex1);
        String secondWord = getWord(columnIndex2);
        String operationResult = getOperationResult(firstWord, secondWord, operation);
        setWord(columnIndex3, operationResult);
    }

    public void addressColumnOperation(int rowIndex1, int rowIndex2, int rowIndex3, Operation operation) {
        String firstAddressColumn = getAddressColumn(rowIndex1);
        String secondAddressColumn = getAddressColumn(rowIndex2);
        String operationResult = getOperationResult(firstAddressColumn, secondAddressColumn, operation);
        setAddressColumn(rowIndex3, operationResult);
    }

    private String getOperationResult(String first, String second, Operation operation) {
        StringBuilder result = new StringBuilder();
        BiFunction<Boolean, Boolean, Boolean> operationFunction = OPERATIONS_MAP.get(operation);
        for (int i = 0; i < first.length(); i++) {
            boolean value1 = first.charAt(i) != '0';
            boolean value2 = second.charAt(i) != '0';
            boolean resultValue = operationFunction.apply(value1, value2);
            result.append(resultValue ? '1' : '0');
        }

        return result.toString();

    }

    public void arithmeticOperations(String key) {
        for (int i = 0; i < SIZE; i++) {
            String word = getWord(i);
            if (word.substring(0, 3).equals(key)) {
                int first = Integer.parseInt(word.substring(3, 7), 2);
                int second = Integer.parseInt(word.substring(7, 11), 2);

                int sum = first + second;

                StringBuilder binarySum = new StringBuilder(Integer.toBinaryString(sum));
                while (binarySum.length() < 5) {
                    binarySum.insert(0, "0");
                }

                String result = word.substring(0, 11) + binarySum;
                setWord(i, result);
            }
        }
    }


    private boolean getG(boolean g, int a, int s, boolean l) {
        boolean aBool = a != 0;
        boolean sBool = s != 0;

        return g || (!aBool && sBool && !l);
    }

    private boolean getL(boolean l, int a, int s, boolean g) {
        boolean aBool = a != 0;
        boolean sBool = s != 0;

        return l || (aBool && !sBool && !g);
    }

    private int compareAAndS(String s, String a) {
        boolean g = false;
        boolean l = false;
        for (int i = 0; i < s.length(); i++) {
            int ai = Character.getNumericValue(a.charAt(i));
            int si = Character.getNumericValue(s.charAt(i));
            g = getG(g, ai, si, l);
            l = getL(l, ai, si, g);
        }
        if (g == l) {
            return 0;
        }
        if (g && !l) return 1;

        else return -1;
    }

    public List<String> getWordsInRange(String min, String max){
        if (compareAAndS(max, min) == -1){
            getWordsInRange(max, min);
        }

        List<String> allWords = new ArrayList<>();

        for(int i = 0; i< SIZE; i++){
            allWords.add(getWord(i));
        }

        List<String> result = new ArrayList<>();

        for(String word : allWords){
            if (compareAAndS(word, min) != -1 && compareAAndS(word, max) != 1){
                result.add(word);
            }
        }
        return result;
    }



}
