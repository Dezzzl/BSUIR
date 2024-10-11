package org.example;

public class FixedBinary {
    private final String decimalPart;
    private final String fractionalPart;
    private final int sign;

    public FixedBinary(String decimalPart, String fractionalPart, int sign) {
        this.decimalPart = decimalPart;
        this.fractionalPart = fractionalPart;
        this.sign = sign == 1 ? -1 : 1;
    }

    @Override
    public String toString() {
        return Character.toString(sign).concat(decimalPart.concat(fractionalPart));
    }

    public float toDecimal() {
        float decimalPart = 0.0f;
        float fractionalPart = 0.0f;
        for (int i = 0; i < this.decimalPart.length(); i++) {
            decimalPart += (float) (this.decimalPart.charAt(i) - '0') * Math.pow(2.0, this.decimalPart.length() - 1 - i);
        }
        for (int i = 0; i < this.fractionalPart.length(); i++) {
            fractionalPart += (float) (this.fractionalPart.charAt(i) - '0') * Math.pow(2.0, -(i + 1));
        }
        float result = decimalPart + fractionalPart;
        return result * this.sign;
    }
}
