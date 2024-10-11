package org.example;

public class BinaryFloat {
    private static final int NUMBER_OF_BITS = 32;
    private int[] binaryFloat = new int[NUMBER_OF_BITS];

    public BinaryFloat(float decimalFloat) {
        formBinaryFloat(decimalFloat);
    }

    private BinaryFloat(int[] binaryFloat) {
        this.binaryFloat = binaryFloat;
    }

    public void formBinaryFloat(float decimalFloat) {
        binaryFloat[0] = decimalFloat > 0 ? 0 : 1;
        decimalFloat = Math.abs(decimalFloat);
        int intValue = (int) decimalFloat;
        float decimalPart = decimalFloat - intValue;
        StringBuilder wholeNumber = new StringBuilder();
        while (intValue > 0) {
            wholeNumber.insert(0, intValue % 2);
            intValue = intValue / 2;
        }
        StringBuilder decimalNumber = new StringBuilder();
        while (decimalPart != 0.0) {
            decimalPart = decimalPart * 2.0f;
            if (decimalPart < 1) {
                decimalNumber.append("0");
            } else {
                decimalNumber.append("1");
                decimalPart = decimalPart - 1.0f;
            }
        }

        int count = 0;
        if (!wholeNumber.isEmpty()) while (wholeNumber.charAt(count) != '1') count++;
        else while (decimalNumber.charAt(count) != '1') count++;
        int degree = !wholeNumber.isEmpty() ? wholeNumber.length() - count - 1 : -count - 1;
        if (!wholeNumber.isEmpty()) wholeNumber.replace(0, count + 1, "");
        else decimalNumber.replace(0, count + 1, "");
        String mantis = wholeNumber.append(decimalNumber.toString()).toString();
        int offsetDegree = degree + 127;
        for (int i = 8; i > 0; i--) {
            binaryFloat[i] = offsetDegree % 2;
            offsetDegree = offsetDegree / 2;
        }

        for (int i = 9; i < mantis.length() + 9; i++) {
            binaryFloat[i] = Character.getNumericValue(mantis.charAt(i - 9));
        }
    }

    public double binaryFloatToDecimal() {
        int sign = binaryFloat[0] == 0 ? 1 : -1;
        int exponent = exponentToDecimal() - 127;
        float decimalNumber = 0;
        String mantissa = "1" + mantisaToString();
        for (int i = 0; i < mantissa.length(); i++) {
            decimalNumber += mantissa.charAt(i) == '1' ? (float) Math.pow(2.0, exponent) : 0;
            exponent--;
        }
        return sign * decimalNumber;
    }


    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_BITS; i++) {
            answer.append(binaryFloat[i]);
        }
        return answer.toString();
    }

    public BinaryFloat sum(BinaryFloat first, BinaryFloat second) {
        int resultSign = getSign(first, second);
        int exponentOfFirstSummand = first.exponentToDecimal() - 127;
        int exponentOfSecondSummand = second.exponentToDecimal() - 127;
        int differenceOfExponents = exponentOfFirstSummand - exponentOfSecondSummand;
        if (differenceOfExponents < 0) {
            BinaryFloat buff = first;
            int buffForExponents = exponentOfFirstSummand;
            first = second;
            second = buff;
            differenceOfExponents = -differenceOfExponents;
            exponentOfFirstSummand = exponentOfSecondSummand;
            exponentOfSecondSummand = buffForExponents;
        }
        StringBuilder mantissaOfFirst = new StringBuilder("1" + first.mantisaToString());
        StringBuilder mantissaOfSecond = new StringBuilder("1" + second.mantisaToString());
        for (int i = 0; i < differenceOfExponents; i++) {
            mantissaOfFirst.append("0");
            mantissaOfSecond.insert(0, "0");
        }
        String res;
        if (first.binaryFloat[0] != second.binaryFloat[0])
            res = Binary.subtractBinary(mantissaOfFirst.toString(), mantissaOfSecond.toString());
        else res = Binary.addBinary(mantissaOfFirst.toString(), mantissaOfSecond.toString());
        if (res.length() > mantissaOfFirst.length()) differenceOfExponents++;
        res = res.substring(0, res.length() - differenceOfExponents);
        int resultExponentDecimal = exponentOfSecondSummand + differenceOfExponents + 127;
        String resultExponentBinary = exponentToBinary(resultExponentDecimal);
        String resultMantissa = res.substring(1);
        return combineValues(resultExponentBinary, resultMantissa, resultSign);
    }

    public BinaryFloat combineValues(String resultExponentBinary, String resultMantissa, int resultSign) {
        int totalLength = 1 + resultExponentBinary.length() + resultMantissa.length();
        int[] combinedValues = new int[totalLength];
        combinedValues[0] = resultSign;
        for (int i = 0; i < resultExponentBinary.length(); i++) {
            combinedValues[1 + i] = Character.getNumericValue(resultExponentBinary.charAt(i));
        }
        for (int i = 0; i < resultMantissa.length(); i++) {
            combinedValues[1 + resultExponentBinary.length() + i] = Character.getNumericValue(resultMantissa.charAt(i));
        }
        return new BinaryFloat(combinedValues);
    }






    public int exponentToDecimal() {
        int decimalNumber = 0;
        int power = 0;
        for (int i = 8; i >= 1; i--) {
            decimalNumber += (int) (binaryFloat[i] * Math.pow(2, power));
            power++;
        }
        return decimalNumber;
    }

    private String exponentToBinary(int decimal) {
        if (decimal == 0) {
            return "0";
        }
        StringBuilder binary = new StringBuilder();
        int counter = 0;
        while (decimal > 0) {
            int remainder = decimal % 2;
            binary.insert(0, remainder);
            decimal = decimal / 2;
            counter++;
        }
        while (counter < 8) {
            binary.insert(0, "0");
            counter++;
        }
        return binary.toString();
    }


    private String mantisaToString() {
        StringBuilder mantissa = new StringBuilder();
        for (int i = 9; i < NUMBER_OF_BITS; i++) {
            mantissa.append(binaryFloat[i]);
        }
        return mantissa.toString();
    }

    private int getSign(BinaryFloat first, BinaryFloat second) {
        if (first.binaryFloat[0] == second.binaryFloat[0]) return first.binaryFloat[0];
        int exponentOfFirst = first.exponentToDecimal();
        int exponentOfSecond = second.exponentToDecimal();
        if (exponentOfFirst > exponentOfSecond) return first.binaryFloat[0];
        else if (exponentOfFirst < exponentOfSecond) return second.binaryFloat[0];
        else {
            int comparingResult = compareMantissa(first, second);
            if (comparingResult == 1) return first.binaryFloat[0];
            else return second.binaryFloat[0];
        }
    }

    private int compareMantissa(BinaryFloat first, BinaryFloat second) {
        for (int i = 9; i < NUMBER_OF_BITS; i++) {
            if (first.binaryFloat[i] != second.binaryFloat[i]) {
                return first.binaryFloat[i] > second.binaryFloat[i] ? 1 : 0;
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        int a = 10;
        BinaryFloat binaryFloat1 = new BinaryFloat(-27.325f);
        BinaryFloat binaryFloat2 = new BinaryFloat(-0.125f);
        System.out.println(binaryFloat1.binaryFloatToDecimal());
        System.out.println(binaryFloat1);
//        System.out.println(binaryFloat1.exponentToDecimal());
        BinaryFloat binaryFloat3 = binaryFloat1.sum(binaryFloat1, binaryFloat2);
        System.out.println(binaryFloat3.binaryFloatToDecimal());
    }
}