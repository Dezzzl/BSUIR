package main.java.com.dezzzl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputPolynomial {

    private static final Scanner scanner = new Scanner(System.in);
    public  Polynomial inputPolynomial(){
        boolean isPolynomial=true;
        while(true) {
            System.out.println("Введите степень многочлена");
            int degree = inputDegree();
            System.out.println("Введите коэффициенты многочлена");
            List<Double> coefficients = inputCoefficients(degree);
            Polynomial polynomial= new Polynomial(coefficients, degree);
          isPolynomial=polynomial.isPolynomial();
          if(isPolynomial)return polynomial;
        }
    }

    private List<Double> inputCoefficients(int degree) {
        List<Double>coefficients=new ArrayList<>();
        int coefficientNumber=0;
        while(coefficientNumber<=degree){
            System.out.println("Введите "+coefficientNumber+" коэффициент многочлена");
            coefficients.add(scanner.nextDouble());
            coefficientNumber++;
        }
        return coefficients;
    }

    private int inputDegree(){
        return scanner.nextInt();
    }

}
