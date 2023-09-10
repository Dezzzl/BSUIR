package main.java.com.dezzzl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Polynomial {
    private final List<Double> coefficients;

    private int degree;

    /**
     * Конструктор класса, создающий многочлен на основе заданных коэффициентов и степени.
     *
     * @param coefficients Список коэффициентов многочлена.
     * @param degree       Степень многочлена.
     */
    public Polynomial(List<Double> coefficients, int degree) {
        this.coefficients = coefficients;
        this.degree = degree;
    }

    /**
     * Конструктор класса, создающий пустой многочлен заданной степени.
     *
     * @param degree Степень многочлена.
     */
    public Polynomial(int degree) {
        this.degree = degree;
        coefficients = new ArrayList<>(degree + 1);
    }

    /**
     * Устанавливает значение коэффициента по заданному индексу.
     *
     * @param coefficientNumber Степень при коэффициенте.
     * @param coefficient       Значение коэффициента которое будет установлено у данной степени.
     */
    public void setCoefficient(int coefficientNumber, double coefficient) {
        coefficients.set(coefficientNumber, coefficient);
    }

    /**
     * Устанавливает степень многочлена.
     *
     * @param degree степень многочлена.
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * Возвращает многочлен в виде строки.
     *
     * @return Многочлен в виде строки.
     */

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
        if (polynomial.length() > 2 && polynomial.charAt(polynomial.length() - 1) == ' ') {
            polynomial.replace(polynomial.length() - 3, polynomial.length(), "");
        }
        return polynomial.toString();
    }

    /**
     * Возвращает количество коэффициентов не равных 0 м ногочлене.
     *
     * @return количество коэффициентов не равных 0 м ногочлене.
     */
    public int getCountOfNotZeroCoefficients() {
        int countOfCoefficientsNotEqualToZero = 0;
        for (int coefficientNumber = 0; coefficientNumber <= this.getDegree(); coefficientNumber++) {
            if (this.getCoefficient(coefficientNumber) != 0) countOfCoefficientsNotEqualToZero++;
        }
        return countOfCoefficientsNotEqualToZero;
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

    /**
     * Возвращает степень многочлена.
     *
     * @return степень многочлена.
     */
    public int getDegree() {
        return degree;
    }

    /**
     * Возвращает коэффициент при переданной степени.
     *
     * @param coefficientNumber степень при коэффициенте, который хотим получить.
     * @return коэффициент при переданной степени.
     */

    public double getCoefficient(int coefficientNumber) {
        return coefficients.get(coefficientNumber);
    }

    /**
     * Расчитывает значение многочлена для заданного аргумента
     *
     * @param argument значение аргумента
     * @return значение многочлена для заданного аргумента
     */
    public double calculateTheValue(double argument) {
        double polynomialValue = 0;
        for (int coefficientNumber = degree; coefficientNumber > 0; coefficientNumber--) {
            double valueAtTheCoefficient = getCoefficient(coefficientNumber) * Math.pow(argument, ((double) coefficientNumber));
            polynomialValue += valueAtTheCoefficient;
        }
        polynomialValue += getCoefficient(0);
        return polynomialValue;
    }

    /**
     * Выполняет сложение многочлена с другим многочленом.
     *
     * @param other Другой многочлен.
     * @return Результат сложения многочленов.
     */
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


    /**
     * Выполняет деление многочлена на другой многочлен и возвращает как результат
     * частное и остаток от деления в виде списка многочленов.
     *
     * @param divisor Делитель (многочлен, на который делится текущий многочлен).
     * @return Список, содержащий частное и остаток от деления.
     */
    public List<Polynomial> divide(Polynomial divisor) {
        List<Polynomial> quotientParts = new ArrayList<>();
        Polynomial remainder = this;
        if (this.getDegree() < divisor.getDegree()) {
            remainder = divisor;
            divisor = this;
        }
        List<Polynomial> remainderAndQuotient = new ArrayList<>();
        while (true) {
            Polynomial divisionResult = findQuotientTerm(remainder, divisor);
            if (divisionResult != null) {
                quotientParts.add(divisionResult);
                Polynomial product = multiplyQuotientByDivisor(divisionResult, divisor);
                remainder = subtractProductFromRemainder(remainder, product);
            } else {
                remainderAndQuotient.add(remainder);
                break;
            }
        }
        Polynomial quotient = calculateQuotient(quotientParts);
        remainderAndQuotient.add(quotient);
        return remainderAndQuotient;
    }

    private Polynomial calculateQuotient(List<Polynomial> quotientParts) {
        Polynomial quotient = quotientParts.get(0);
        for (int part = 0; part < quotientParts.size() - 1; part++) {
            quotient = quotient.addition(quotientParts.get(part + 1));
        }
        return quotient;
    }

    private Polynomial subtractProductFromRemainder(Polynomial remainder, Polynomial product) {
        return remainder.subtract(product);
    }

    private Polynomial multiplyQuotientByDivisor(Polynomial quotient, Polynomial divisor) {
        return divisor.multiply(quotient);
    }

    private Polynomial findQuotientTerm(Polynomial remainder, Polynomial divisor) {
        if (divisor.getDegree() > remainder.getDegree()) {
            return null;
        } else {
            double leadingCoefficient = remainder.getCoefficient(remainder.getDegree()) / divisor.getCoefficient(divisor.getDegree());
            int degree = remainder.getDegree() - divisor.getDegree();
            Polynomial divisionResult = new Polynomial(degree);
            for (int coefficientNumber = 0; coefficientNumber < divisionResult.getDegree(); coefficientNumber++) {
                divisionResult.coefficients.add(0.0);
            }
            divisionResult.coefficients.add(leadingCoefficient);
            return divisionResult;
        }
    }

    /**
     * Выполняет вычитание другого многочлена из данного многочлена.
     *
     * @param other Другой многочлен.
     * @return Результат вычитания многочленов.
     */
    public Polynomial subtract(Polynomial other) {
        Polynomial oppositePolynomial = makeOpposite(other);
        return addition(oppositePolynomial);
    }

    /**
     * Выполняет умножение многочлена на другой многочлен.
     *
     * @param second Второй многочлен.
     * @return Результат умножения многочленов.
     */
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


    /**
     * Проверяет равенство текущего многочлена другому объекту.
     *
     * @param o Объект для сравнения.
     * @return true, если объекты равны, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        return degree == that.degree && Objects.equals(coefficients, that.coefficients);
    }

    /**
     * Вычисляет хэш-код для текущего многочлена.
     *
     * @return Хэш-код многочлена.
     */
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
