package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1-Ввод СКНФ/СДНФ");
            System.out.println("2- Выход");
            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Выберите тип выражения\n1-СКНФ\n2-СДНФ");
                int formulaTypeChoice = scanner.nextInt();
                FormulaType formulaType;
                if (formulaTypeChoice == 1) {
                    formulaType = FormulaType.PCNF;
                } else formulaType = FormulaType.PDNF;
                System.out.println("Введите выражение: ");
                String formula = scanner.next();
                System.out.println("Расчетный метод: ");
                Parser parser = new Parser();
                List<List<Integer>> numericFormula = parser.parse(formula, formulaType);
                CalculationMethod calculationMethod1 = new CalculationMethod(formula, numericFormula);
                if (formulaType == FormulaType.PDNF)
                    System.out.println(calculationMethod1.getDnfWithCalculationMethod());
                else System.out.println(calculationMethod1.getCnfWithCalculationMethod());
                System.out.println("Расчетно-табличный метод: ");
                CalculationMethod calculationMethod2 = new CalculationMethod(formula, numericFormula);
                List<List<Integer>> mergedFormula = calculationMethod2.BondingStep();
                TabularMethod tabularMethod = new TabularMethod(formula, numericFormula, mergedFormula);
                tabularMethod.showTable(formulaType);
                List<List<Integer>> finalFormula = tabularMethod.removeRedundantImplicants();
                if (formulaType == FormulaType.PDNF) System.out.println(FormulaUtil.createDNF(formula, finalFormula));
                else System.out.println(FormulaUtil.createCNF(formula, finalFormula));
//                System.out.println("Таблиный метод: ");
//                TableMethod tableMethod = new TableMethod(formula, numericFormula, formulaType);
//                System.out.println(tableMethod.Minimize(formulaType));
//                System.out.println("Карта Карно: ");
//                tableMethod.printCarnoTable();
            } else break;

        }
    }
}