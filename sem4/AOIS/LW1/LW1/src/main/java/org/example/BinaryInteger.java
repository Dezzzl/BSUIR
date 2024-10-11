package org.example;

public class BinaryInteger {
    private final int[] binaryInt;
    private final int numberOfBits;
    public BinaryInteger(int decimalInt) {
        numberOfBits = 8;
        this.binaryInt = new int[numberOfBits];
        toBinary(decimalInt);
    }


    public BinaryInteger(int[] binaryInt, int numberOfBits) {
        this.binaryInt = binaryInt;
        this.numberOfBits = numberOfBits;
    }

    public int getSign() {
        return this.binaryInt[0];
    }

    public String getModule() {
        StringBuilder module = new StringBuilder();
        for (int i = 1; i < numberOfBits; i++) {
            module.append(binaryInt[i]);
        }
        return module.toString();
    }

    public void setSign(int sign) {
        this.binaryInt[0] = sign;
    }

    private void toBinary(int decimalInt) {
        if (decimalInt < 0) {
            binaryInt[0] = 1;
            decimalInt = -decimalInt;
        } else binaryInt[0] = 0;
        for (int i = numberOfBits - 1; i > 0; i--) {
            binaryInt[i] = decimalInt % 2;
            decimalInt = decimalInt / 2;
        }
    }

    private int[] directCode() {
        return binaryInt.clone();
    }

    public int[] reverseCode() {
        int[] reverseCodeInt = binaryInt.clone();
        if (reverseCodeInt[0] == 1) {
            makeReverse(reverseCodeInt);
        }
        return reverseCodeInt;
    }

    public int[] additionalCode() {
        int[] additionalCodeInt = null;
        if (binaryInt[0] == 1) {
            additionalCodeInt = reverseCode();
            addOneToTheNumber(additionalCodeInt);
        } else additionalCodeInt = binaryInt;
        return additionalCodeInt;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < numberOfBits; i++) {
            answer.append(binaryInt[i]);
        }
        return answer.toString();
    }

    private BinaryInteger reverseBinaryInt() {
        int[] bin = binaryInt.clone();
        bin[0] = bin[0] == 1 ? 0 : 1;
        return new BinaryInteger(bin, binaryInt.length);
    }

    public BinaryInteger additionWithReverseCode(BinaryInteger other) {
        int[] firstSummand = this.reverseCode();
        int[] secondSummand = other.reverseCode();
        int[] resultBinaryInt = new int[numberOfBits];
        int condition = 0;
        for (int i = numberOfBits - 1; i >= 0; i--) {
            resultBinaryInt[i] = (firstSummand[i] + secondSummand[i] + condition) % 2;
            condition = (firstSummand[i] + secondSummand[i] + condition) >= 2 ? 1 : 0;
        }
        if (condition != 0) addOneToTheNumber(resultBinaryInt);
        if (resultBinaryInt[0] == 1) makeReverse(resultBinaryInt);
        return new BinaryInteger(resultBinaryInt, resultBinaryInt.length);
    }

    public BinaryInteger additionWithAdditionalCode(BinaryInteger other) {
        int[] firstSummand = this.additionalCode();
        int[] secondSummand = other.additionalCode();
        int[] resultBinaryInt = new int[numberOfBits];
        int condition = 0;
        for (int i = numberOfBits - 1; i >= 0; i--) {
            resultBinaryInt[i] = (firstSummand[i] + secondSummand[i] + condition) % 2;
            condition = (firstSummand[i] + secondSummand[i] + condition) >= 2 ? 1 : 0;
        }
        if (resultBinaryInt[0] == 1) {
            makeReverse(resultBinaryInt);
            addOneToTheNumber(resultBinaryInt);
        }
        return new BinaryInteger(resultBinaryInt, resultBinaryInt.length);
    }

    private void addOneToTheNumber(int[] binaryInt) {
        int pointer = numberOfBits - 1;
        while (pointer > 0) {
            if (binaryInt[pointer] == 0) break;
            pointer--;
        }
        binaryInt[pointer] = 1;
        for (int i = pointer + 1; i < numberOfBits; i++) {
            binaryInt[i] = 0;
        }
    }

    private void makeReverse(int[] binaryInt) {
        for (int i = 1; i < numberOfBits; i++) {
            binaryInt[i] = binaryInt[i] == 1 ? 0 : 1;
        }
    }

    public BinaryInteger differenceWithReverseCode(BinaryInteger other) {
        BinaryInteger opposite = new BinaryInteger(other.binaryInt.clone(), other.binaryInt.length);
        int sign = opposite.getSign() == 0 ? 1 : 0;
         opposite.setSign(sign);
        return additionWithReverseCode(opposite);
    }

    public BinaryInteger differenceWithAdditionalCode(BinaryInteger first, BinaryInteger second) {
        BinaryInteger opposite = new BinaryInteger(second.binaryInt.clone(), second.binaryInt.length);
        int sign = opposite.getSign() == 0 ? 1 : 0;
        opposite.setSign(sign);
        return first.additionWithAdditionalCode(opposite);
    }

    public BinaryInteger multiplication(BinaryInteger other) {
        int resultSign = (this.getSign() + other.getSign()) % 2;
        StringBuilder result = new StringBuilder("0".repeat(numberOfBits * 2));
        int[] firstMultiplier = this.binaryInt.clone();
        int[] secondMultiplier = other.binaryInt.clone();
        firstMultiplier[0] = 0;
        secondMultiplier[0] = 0;
        int[][] partialProducts = new int[numberOfBits][numberOfBits];
        for (int i = 0; i < numberOfBits; i++) {
            for (int j = 0; j < numberOfBits; j++) {
                partialProducts[i][j] = secondMultiplier[j] * firstMultiplier[i];
            }
        }
        for (int i = 0; i < numberOfBits; i++) {
            int carry = 0;
            for (int j = numberOfBits - 1; j > 0; j--) {
                int sum = Character.getNumericValue(result.charAt(i + j)) + partialProducts[i][j] + carry;
                result.setCharAt(i + j, (char) ((sum % 2) + '0'));
                carry = sum / 2;
            }
        }
        result.deleteCharAt(result.length() - 1);
        result.insert(0, resultSign);
        return new BinaryInteger(stringToIntArray(result.toString()), result.length());
    }

    public static int[] stringToIntArray(String binaryString) {
        int length = binaryString.length();
        int[] intArray = new int[length];

        for (int i = 0; i < length; i++) {
            char ch = binaryString.charAt(i);
            intArray[i] = Character.getNumericValue(ch);
        }

        return intArray;
    }

    public FixedBinary division(BinaryInteger dividend, BinaryInteger divisor) {
        int resultSign = (dividend.getSign() + divisor.getSign()) % 2;
        String dividendForDivision = dividend.getModule().concat("000000000000000");
        String divisorForDivision = divisor.getModule().replaceFirst("^0+", "");
        String windowDividend = "";
        String pr = "";
        for (int i = 0; i < dividendForDivision.length(); i++) {
            windowDividend = windowDividend.concat(Character.toString(dividendForDivision.charAt(i)));
            if (Binary.compareBinaries(windowDividend, divisorForDivision) != -1) {
                pr = pr.concat("1");
                BinaryInteger res = differenceWithAdditionalCode(toBinFromStr(0, windowDividend), toBinFromStr(0, divisorForDivision));
                windowDividend = res.getModule().replaceFirst("^0+", "");
            } else {
                pr = pr.concat("0");
            }
        }
        int lengthOfDecimalPart = dividend.getModule().length();
        return new FixedBinary(pr.substring(0, lengthOfDecimalPart), pr.substring(lengthOfDecimalPart), resultSign);
    }


    private BinaryInteger toBinFromStr(int sign, String binary) {
        int[] binaryInteger = new int[numberOfBits*2];
        binaryInteger[0] = sign;
        for (int i = 0; i < binary.length(); i++) {
            char ch = binary.charAt(binary.length() - 1 - i);
            binaryInteger[numberOfBits*2 - 1 - i] = Character.getNumericValue(ch);
        }

        for (int i = numberOfBits*2 - 1 - binary.length(); i > 0; i--) {
            binaryInteger[i] = 0;
        }

        return new BinaryInteger(binaryInteger, binaryInteger.length);
    }

    public int binaryToInt() {
        int sign = this.binaryInt[0];
        int result = 0;
        for (int i = 1; i < this.numberOfBits; i++) {
            result *= 2;
            result += binaryInt[i];
        }
        if (sign == 1) {
            result = -result;
        }
        return result;
    }
}
