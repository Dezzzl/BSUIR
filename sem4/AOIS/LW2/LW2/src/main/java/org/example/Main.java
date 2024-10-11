package org.example;

import org.example.Exceptions.NotFormulaException;
import org.example.source.Parser;
import org.example.source.PerfectForms;
import org.example.source.TruthTable;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("1-Ввести формулу");
                System.out.println("2-Выйти");
                int choice = scanner.nextInt();
                if (choice==2)break;
                System.out.print("Введите формулу: ");
                String formula = scanner.next();
                Parser parser = new Parser();
                TruthTable truthTable = parser.isValid(formula);
                truthTable.displayTruthTable();
                PerfectForms perfectForms = new PerfectForms(truthTable);
                System.out.println("СДНФ: " + perfectForms.createPDNF());
                System.out.println("СКНФ: " + perfectForms.createPCNF());
                System.out.println("Числовая форма СДНФ: " + perfectForms.NumericFormPDNF());
                System.out.println("Числовая форма СКНФ: " + perfectForms.NumericFormPCNF());
                System.out.println("Индексная форма: " + perfectForms.IndexForm());
            } catch (NotFormulaException e) {
                System.out.println("Введена не формула");
            }
            catch (InputMismatchException e){
                break;
            }
        }
    }
}