package main.java.com.dezzzl;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Polynomial {
    private final List<Double> coefficients;

    private int degree;

    public Polynomial(List<Double> coefficients, int degree) {
        this.coefficients = coefficients;
        this.degree = degree;
    }

    public Polynomial(int degree) {
        this.degree = degree;
        coefficients = new ArrayList<>(degree + 1);
    }

    public void setCoefficient(int coefficientNumber, double coefficient) {
        coefficients.set(coefficientNumber, coefficient);
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

    public double getCoefficient(int coefficientNumber) {
        return coefficients.get(coefficientNumber);
    }

    public double calculateTheValue(double argument) {
        double polynomialValue = 0;
        for (int coefficientNumber = degree; coefficientNumber > 0; coefficientNumber--) {
            double valueAtTheCoefficient = getCoefficient(coefficientNumber) * Math.pow(argument, ((double) coefficientNumber));
            polynomialValue += valueAtTheCoefficient;
        }
        polynomialValue += getCoefficient(0);
        return polynomialValue;
    }

    public Polynomial addition(Polynomial other) {
        Polynomial polynomialWithHighestDegree = getPolynomialWithHighestDegree(other);
        Polynomial polynomialWithSmallestDegree = getPolynomialWithSmallestDegree(other);
        Polynomial resultingPolynomial = new Polynomial(polynomialWithHighestDegree.getDegree());
        for (int coefficientNumber = 0; coefficientNumber <= polynomialWithHighestDegree.getDegree(); coefficientNumber++) {
            resultingPolynomial.coefficients.add(polynomialWithHighestDegree.getCoefficient(coefficientNumber));
        }
        for (int coefficientNumber = polynomialWithSmallestDegree.getDegree(); coefficientNumber >= 0; coefficientNumber--) {
            double resultingCoefficient = resultingPolynomial.getCoefficient(coefficientNumber) + polynomialWithSmallestDegree.getCoefficient(coefficientNumber);
            resultingPolynomial.setCoefficient(coefficientNumber, resultingCoefficient);
        }
        return resultingPolynomial;
    }

    public Polynomial difference(Polynomial other) {
        Polynomial oppositePolynomial = makeOpposite(other);
        return addition(oppositePolynomial);
    }

    private Polynomial makeOpposite(Polynomial polynomial) {
        Polynomial oppositePolynomial = new Polynomial(polynomial.getDegree());
        for (int coefficientNumber = 0; coefficientNumber <= polynomial.getDegree(); coefficientNumber++) {
            double oppositeValue = -polynomial.getCoefficient(coefficientNumber);
            oppositePolynomial.coefficients.add(oppositeValue);
        }
        return oppositePolynomial;
    }

    private boolean isMinuend(Polynomial polynomial) {
        return equals(polynomial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return degree == that.degree && Objects.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficients, degree);
    }

    private Polynomial getPolynomialWithHighestDegree(Polynomial other) {
        return this.getDegree() >= other.getDegree() ? this : other;
    }

    private Polynomial getPolynomialWithSmallestDegree(Polynomial other) {
        if (this.getDegree() < other.getDegree()) {
            return this;
        } else {
            return other;
        }
    }

}
