package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean flag = true;
        while(flag) {

            System.out.println("Введите 2 числа");
            int a=2, b=3;
            float d = 2.3f;
            float e = 13.5f;
            System.out.println("Целые - 1, вещественные - 2");
            Scanner scanner = new Scanner(System.in);
            int f = scanner.nextInt();
            if (f ==1){
                System.out.println("1-ое целое число");
                a= scanner.nextInt();
                System.out.println("2-ое целое число");
                b= scanner.nextInt();
            }
            else {
                System.out.println("1-ое вещественное число");
                d = scanner.nextFloat();
                System.out.println("2-ое вещественное число");
                e = scanner.nextFloat();
            }
            BinaryInteger binaryInteger1 = new BinaryInteger(a);
            BinaryInteger binaryInteger2= new BinaryInteger(b);

            int choice;
            System.out.println("1-сложение");
            System.out.println("2-вычитание");
            System.out.println("3-умножение");
            System.out.println("4-деление");
            System.out.println("5-сложение вещественных чисел");
            choice=scanner.nextInt();
            BinaryInteger binaryInteger3;
            switch(choice){
                case 1:{
                    binaryInteger3= binaryInteger1.additionWithAdditionalCode(binaryInteger2);
                    System.out.println(binaryInteger3.binaryToInt());
                    System.out.println(binaryInteger3.toString());
                    break;
                }
                case 2:{
                    binaryInteger3= binaryInteger1.differenceWithAdditionalCode(binaryInteger1,binaryInteger2);
                    System.out.println(binaryInteger3.binaryToInt());
                    System.out.println(binaryInteger3.toString());
                    break;
                }
                case 3:{
                    binaryInteger3= binaryInteger1.multiplication(binaryInteger2);
                    System.out.println(binaryInteger3.binaryToInt());
                    System.out.println(binaryInteger3.toString());
                    break;
                }
                case 4:{
                    FixedBinary fixedBinary = binaryInteger1.division(binaryInteger1, binaryInteger2);
                    System.out.println(fixedBinary.toString());
                    System.out.println(fixedBinary.toDecimal());
                    break;
                }
                case 5:{
                    BinaryFloat binaryFloat1 = new BinaryFloat(d);
                    BinaryFloat binaryFloat2 = new BinaryFloat(e);
                    BinaryFloat sum = binaryFloat1.sum(binaryFloat1, binaryFloat2);
                    System.out.println(sum.toString());
                    System.out.println(sum.binaryFloatToDecimal());
                }

            }
//            BinaryInteger binaryInteger = new BinaryInteger(3);
//            BinaryInteger binaryInteger2 = new BinaryInteger(7);
//            BinaryInteger checker = new BinaryInteger(0);
//            System.out.println(binaryInteger);
//            System.out.println(binaryInteger2);
//            System.out.println(binaryInteger2);
//            System.out.println(0 % 2);
//            System.out.println(1 % 2);
//            System.out.println(2 % 2);
//            System.out.println(3 % 2);
//
//            System.out.println(binaryInteger.additionWithReverseCode(binaryInteger2));
//            System.out.println(binaryInteger.additionWithAdditionalCode(binaryInteger2));
//            System.out.println(checker);
//            BinaryInteger binaryInteger1 = binaryInteger.multiplication(binaryInteger2);
//            System.out.println(binaryInteger1.binaryToInt());
////        BinaryInteger difference = binaryInteger.differenceWithAdditionalCode(binaryInteger, binaryInteger2);
//            FixedBinary division = binaryInteger.division(binaryInteger, binaryInteger2);
//            System.out.println(division.toDecimal());
        }
    }
}