package org.example;

import java.util.List;

public class Task2 {
    private static final List<List<Integer>> setsForY4 = List.of(List.of(0, 0, 1, 1), List.of(0, 1, 0,0 ), List.of(0, 1, 0, 1), List.of(0, 1, 1, 0),List.of(0, 1, 1, 1), List.of(1, 0, 0, 0), List.of(1, 0, 0, 1));
    private static final List<List<Integer>> setsForY3 = List.of(List.of(0, 0, 0, 0), List.of(0, 0, 0,1 ), List.of(0, 0, 1, 0), List.of(0, 1, 1, 1), List.of(1, 0, 0, 0), List.of(1, 0, 0, 1));
    private static final List<List<Integer>> setsForY2 = List.of(List.of(0, 0, 0, 1), List.of(0, 0, 1,0 ), List.of(0, 1, 0, 1), List.of(0, 1, 1, 0), List.of(1, 0, 0, 1));
    private static final List<List<Integer>> setsForY1 = List.of(List.of(0, 0, 0, 0), List.of(0, 0, 1,0 ), List.of(0, 1, 0, 0), List.of(0, 1, 1, 0), List.of(1, 0, 0, 0));

    public static void main(String[] args) {
        System.out.println("1 часть:");
        System.out.println(" Одноразрядный двоичный сумматор на 3 входа (ОДС-3) с представлением выходных функций в СДНФ");
        String f4 = FormulaUtil.createDNF("(D/\\C/\\B/\\A)", setsForY4);
        String  f3= FormulaUtil.createDNF("(D/\\C/\\B/\\A)", setsForY3);
        String f2 = FormulaUtil.createDNF("(D/\\C/\\B/\\A)", setsForY2);
        String f1 = FormulaUtil.createDNF("(D/\\C/\\B/\\A)", setsForY1);
        System.out.println("СДНФ для f4: "+ f4);
        System.out.println("СДНФ для f3: "+ f3);
        System.out.println("СДНФ для f2: "+ f2);
        System.out.println("СДНФ для f1: "+ f1);
        CalculationMethod calculationMethod1 = new CalculationMethod(f4, setsForY4);
        List<List<Integer>> mergedFormula1 = calculationMethod1.BondingStep();
        TabularMethod tabularMethod1 = new TabularMethod(f4, setsForY4, mergedFormula1);
        tabularMethod1.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula1 = tabularMethod1.removeRedundantImplicants();
        System.out.println("Минимизированная формула для f4:");
        System.out.println(FormulaUtil.createDNF(f4, finalFormula1));
        System.out.println("...........................");
        CalculationMethod calculationMethod2 = new CalculationMethod(f3, setsForY3);
        List<List<Integer>> mergedFormula2 = calculationMethod2.BondingStep();
        TabularMethod tabularMethod2 = new TabularMethod(f3, setsForY3, mergedFormula2);
        tabularMethod2.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula2 = tabularMethod2.removeRedundantImplicants();
        System.out.println("Минимизированная формула для f3:");
        System.out.println(FormulaUtil.createDNF(f3, finalFormula2));
        System.out.println("...........................");
        CalculationMethod calculationMethod3 = new CalculationMethod(f2, setsForY2);
        List<List<Integer>> mergedFormula3 = calculationMethod3.BondingStep();
        TabularMethod tabularMethod3 = new TabularMethod(f2, setsForY2, mergedFormula3);
        tabularMethod3.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula3 = tabularMethod3.removeRedundantImplicants();
        System.out.println("Минимизированная формула для f2:");
        System.out.println(FormulaUtil.createDNF(f2, finalFormula3));
        System.out.println("...........................");
        CalculationMethod calculationMethod4 = new CalculationMethod(f1, setsForY1);
        List<List<Integer>> mergedFormula4 = calculationMethod4.BondingStep();
        TabularMethod tabularMethod4 = new TabularMethod(f1, setsForY1, mergedFormula4);
        tabularMethod4.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula4 = tabularMethod4.removeRedundantImplicants();
        System.out.println("Минимизированная формула для f1:");
        System.out.println(FormulaUtil.createDNF(f1, finalFormula4));
        System.out.println("...........................");
    }
}
