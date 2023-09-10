package main.java.com.dezzzl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputPolynomial inputPolynomial=new InputPolynomial();

        Polynomial polynomial1=inputPolynomial.inputPolynomial();
        Polynomial polynomial2=inputPolynomial.inputPolynomial();
        System.out.println(polynomial1);
        System.out.println(polynomial2);
        Polynomial result=polynomial1.addition(polynomial2);
        Polynomial dif1=polynomial1.subtract(polynomial2);
        Polynomial dif2=polynomial2.subtract(polynomial1);
        Polynomial mult=polynomial1.multiply(polynomial2);
        List<Polynomial>modAndQuo=polynomial1.divide(polynomial2);
        System.out.println(polynomial1.calculateTheValue(1));
        System.out.println(result);
        System.out.println(dif1);
        System.out.println(dif2);
        System.out.println(mult);
        System.out.println("остаток: "+modAndQuo.get(0));
        System.out.println("частное: "+modAndQuo.get(1));
    }
}
