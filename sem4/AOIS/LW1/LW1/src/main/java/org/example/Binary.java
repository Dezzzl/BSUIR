package org.example;

public class Binary {
    public static int compareBinaries(String firstBinary, String secondBinary) {
        int maxLength = Math.max(firstBinary.length(), secondBinary.length());
        firstBinary = String.format("%" + maxLength + "s", firstBinary).replace(' ', '0');
        secondBinary = String.format("%" + maxLength + "s", secondBinary).replace(' ', '0');

        for (int i = 0; i < maxLength; i++) {
            if (firstBinary.charAt(i) != secondBinary.charAt(i)) {
                return firstBinary.charAt(i) > secondBinary.charAt(i) ? 1 : -1;
            }
        }
        return 0;
    }

//    public static BinaryInteger toBinFromStr(int sign, String binary, int size){
//        int[] binaryInteger = new int[size];
//        binaryInteger[0] = sign;
//        for (int i = 0; i < binary.length(); i++) {
//            char ch = binary.charAt(binary.length() - 1 - i);
//            binaryInteger[size-1-i] = Character.getNumericValue(ch);
//        }
//
//        for (int i = size - 1 - binary.length(); i > 0; i--) {
//            binaryInteger[i] = 0;
//        }
//
//        return new BinaryInteger(binaryInteger);
//    }


//    private static BinaryInteger toBinFromStr(int sign, String binary) {
//        int[] binaryInteger = new int[numberOfBits];
//        binaryInteger[0] = sign;
//        for (int i = 0; i < binary.length(); i++) {
//            char ch = binary.charAt(binary.length() - 1 - i);
//            binaryInteger[numberOfBits - 1 - i] = Character.getNumericValue(ch);
//        }
//
//        for (int i = numberOfBits - 1 - binary.length(); i > 0; i--) {
//            binaryInteger[i] = 0;
//        }
//
//        return new BinaryInteger(binaryInteger, binaryInteger.length);
//    }


    public static String subtractBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0 || j >= 0) {
            int diff = borrow;
            if (i >= 0) {
                diff += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                diff -= b.charAt(j) - '0';
                j--;
            }
            if (diff < 0) {
                diff += 2;
                borrow = -1;
            } else {
                borrow = 0;
            }
            result.insert(0, diff);
        }

        // Удаляем ведущие нули
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }

        return result.toString();
    }

    public static String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i--) - '0';
            }
            if (j >= 0) {
                sum += b.charAt(j--) - '0';
            }
            result.insert(0, sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) {
            result.insert(0, carry);
        }
        return result.toString();
    }

}
