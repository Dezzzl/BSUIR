package main.java.com.dezzzl;

public class Main {
    public static void main(String[] args) {
        InputPolynomial inputPolynomial=new InputPolynomial();

        Polynomial polynomial1=inputPolynomial.inputPolynomial();
        Polynomial polynomial2=inputPolynomial.inputPolynomial();
        polynomial1.displayPolynomial();
        polynomial2.displayPolynomial();
        Polynomial result=polynomial1.addition(polynomial2);
        Polynomial dif1=polynomial1.subtract(polynomial2);
        Polynomial dif2=polynomial2.subtract(polynomial1);
        Polynomial mult=polynomial1.multiply(polynomial2);
        System.out.println(polynomial1.calculateTheValue(1));
        result.displayPolynomial();
        dif1.displayPolynomial();
        dif2.displayPolynomial();
        mult.displayPolynomial();
    }
}
