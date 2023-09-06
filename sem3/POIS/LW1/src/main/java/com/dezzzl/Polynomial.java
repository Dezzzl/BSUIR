package main.java.com.dezzzl;


import java.util.ArrayList;
import java.util.List;


public class Polynomial {
    private final List<Double> coefficients;

    private int degree;

    public Polynomial(List<Double> coefficients, int degree) {
        this.coefficients = coefficients;
        this.degree = degree;
    }


    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void displayPolynomial() {
        StringBuilder polynomial = new StringBuilder();
        for (int coefficientNumber = degree; coefficientNumber >= 0; coefficientNumber--) {
            if (isTheCoefficientNotEqualToZero(coefficients.get(coefficientNumber))) {
                polynomial.append(coefficients.get(coefficientNumber));
            }
            if ((!isTheCoefficientNumberEqualToOne(coefficientNumber)) && (isTheCoefficientNumberNotEqualToZero(coefficientNumber))) {
                polynomial.append("x^" + coefficientNumber);
            }
            if (isTheCoefficientNumberEqualToOne(coefficientNumber)) {
                polynomial.append("x");
            }
            if (isTheCoefficientNumberNotEqualToZero(coefficientNumber)) {
                polynomial.append(" + ");
            }
        }
        System.out.println(polynomial);
    }

    private boolean isTheCoefficientNotEqualToZero(double coefficient) {
        return coefficient != 0;
    }

    private boolean isTheCoefficientNumberNotEqualToZero(int coefficientNumber) {
        return coefficientNumber != 0;
    }

    private boolean isTheCoefficientNumberEqualToOne(int coefficientNumber) {
        return coefficientNumber == 1;
    }

    public int getDegree() {
        return degree;
    }

    public Polynomial Addition(Polynomial other) {
        int degree = Math.max(this.degree, other.degree);
        List<Double> coefficients = new ArrayList<>();

    }

}
