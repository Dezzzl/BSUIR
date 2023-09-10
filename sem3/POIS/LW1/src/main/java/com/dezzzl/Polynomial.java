package main.java.com.dezzzl;


import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public String toString() {
        StringBuilder polynomial = new StringBuilder();
        for (int coefficientNumber = degree; coefficientNumber >= 0; coefficientNumber--) {
            if (isTheCoefficientNotEqualToZero(coefficients.get(coefficientNumber))) {
                polynomial.append(coefficients.get(coefficientNumber));
            } else continue;
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
        if (polynomial.charAt(polynomial.length() - 1) == ' ') {
            polynomial.replace(polynomial.length() - 2, polynomial.length() - 1, "");
        }
        return polynomial.toString();
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
        while (resultingPolynomial.getCoefficient(resultingPolynomial.getDegree()) == 0 && resultingPolynomial.getDegree() > 0) {
            resultingPolynomial.coefficients.remove(resultingPolynomial.getDegree());
            resultingPolynomial.setDegree(resultingPolynomial.getDegree() - 1);
        }
        return resultingPolynomial;
    }

    public List<Polynomial> divide(Polynomial divider) {
        if (this.getDegree() < divider.getDegree()) return null;
        List<Polynomial> partsOfQuotient = new ArrayList<>();
        Polynomial mod = this;
        List<Polynomial> modAndQuotient = new ArrayList<>();
        while (true) {
            Polynomial result = findTheCoeffAndDegree(mod, divider);
            if (result != null) {
                partsOfQuotient.add(result);
                Polynomial res = multiplyTheDividerByTheResult(divider, result);
                mod = subtractTheResultFromTheDivisor(mod, res);
            } else {
                modAndQuotient.add(mod);
                break;
            }
        }
        Polynomial quotient = getQuotient(partsOfQuotient);
        modAndQuotient.add(quotient);
        return modAndQuotient;
    }

    private Polynomial getQuotient(List<Polynomial> partsOfQuotient) {
        Polynomial quotient = partsOfQuotient.get(0);
        for (int part = 0; part < partsOfQuotient.size() - 1; part++) {
            quotient = quotient.addition(partsOfQuotient.get(part + 1));
        }
        return quotient;
    }

    private Polynomial subtractTheResultFromTheDivisor(Polynomial divisor, Polynomial result) {
        return divisor.subtract(result);
    }

    private Polynomial multiplyTheDividerByTheResult(Polynomial divider, Polynomial result) {
        return divider.multiply(result);
    }

    private Polynomial findTheCoeffAndDegree(Polynomial mod, Polynomial divider) {
        if (divider.getDegree() > mod.getDegree()) {
            return null;
        } else {
            double seniorCoefficient = mod.getCoefficient(mod.getDegree()) / divider.getCoefficient(divider.getDegree());
            int degree = mod.getDegree() - divider.getDegree();
            Polynomial result = new Polynomial(degree);
            for (int coefficientNumber = 0; coefficientNumber < result.getDegree(); coefficientNumber++) {
                result.coefficients.add(0.0);
            }
            result.coefficients.add(seniorCoefficient);
            return result;
        }
    }

    public Polynomial subtract(Polynomial other) {
        Polynomial oppositePolynomial = makeOpposite(other);
        return addition(oppositePolynomial);
    }

    public Polynomial multiply(Polynomial second) {
        List<List<Double>> intermediateResults = createMatrixOfIntermediateResults(second);
        List<Double> resultCoefficients = createCoefficientList(intermediateResults);
        Collections.reverse(resultCoefficients);
        return new Polynomial(resultCoefficients, resultCoefficients.size() - 1);
    }

    private List<Double> createCoefficientList(List<List<Double>> intermediateResults) {
        List<Double> resultCoefficients = new ArrayList<>(intermediateResults.get(intermediateResults.size() - 1));
        for (int i = 0; i < intermediateResults.size() - 1; i++) {
            for (int j = 0; j < intermediateResults.get(i).size(); j++) {
                double sum = resultCoefficients.get(j) + intermediateResults.get(i).get(j);
                resultCoefficients.set(j, sum);
            }
        }
        return resultCoefficients;
    }

    private List<List<Double>> createMatrixOfIntermediateResults(Polynomial second) {
        List<List<Double>> intermediateResults = new ArrayList<>();
        int numberOfZeroesInRow = 0;
        for (int degreeSecond = second.getDegree(); degreeSecond >= 0; degreeSecond--) {
            List<Double> row = new ArrayList<>();
            for (int i = 0; i < numberOfZeroesInRow; i++) {
                row.add(0.0);
            }
            for (int degreeFirst = this.getDegree(); degreeFirst >= 0; degreeFirst--) {
                double product = second.getCoefficient(degreeSecond) * this.getCoefficient(degreeFirst);
                row.add(product);
            }
            numberOfZeroesInRow++;
            intermediateResults.add(row);
        }
        return intermediateResults;
    }


    private Polynomial makeOpposite(Polynomial polynomial) {
        Polynomial oppositePolynomial = new Polynomial(polynomial.getDegree());
        for (int coefficientNumber = 0; coefficientNumber <= polynomial.getDegree(); coefficientNumber++) {
            double oppositeValue = -polynomial.getCoefficient(coefficientNumber);
            oppositePolynomial.coefficients.add(oppositeValue);
        }
        return oppositePolynomial;
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
