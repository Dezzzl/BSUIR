package org.example;

import java.util.List;

public class Task1 {
    private static final  List<List<Integer>> setsForSum = List.of(List.of(0,0,1), List.of(0,1,0), List.of(1,0,0), List.of(1,1,1));
    private static final  List<List<Integer>> setsForPer = List.of(List.of(0,1,1), List.of(1,0,1), List.of(1,1,0), List.of(1,1,1));
    public static void main(String[] args) {
        System.out.println("1 часть:");
        System.out.println(" Одноразрядный двоичный сумматор на 3 входа (ОДС-3) с представлением выходных функций в СДНФ");
        String sum = FormulaUtil.createDNF("(A/\\B/\\L)", setsForSum);
        String per = FormulaUtil.createDNF("(A/\\B/\\L)", setsForPer);
        System.out.println("СДНФ для S: "+ sum);
        System.out.println("СДНФ для P: "+ per);
        CalculationMethod calculationMethod1 = new CalculationMethod(sum, setsForSum);
        List<List<Integer>> mergedFormula1 = calculationMethod1.BondingStep();
        TabularMethod tabularMethod1 = new TabularMethod(sum, setsForSum, mergedFormula1);
        tabularMethod1.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula1 = tabularMethod1.removeRedundantImplicants();
        System.out.println("Минимизированная формула для S:");
        System.out.println(FormulaUtil.createDNF(sum, finalFormula1));
        System.out.println("...........................");
        CalculationMethod calculationMethod2 = new CalculationMethod(sum, setsForPer);
        List<List<Integer>> mergedFormula2 = calculationMethod2.BondingStep();
        TabularMethod tabularMethod2 = new TabularMethod(sum, setsForPer, mergedFormula2);
        tabularMethod2.showTable(FormulaType.PDNF);
        List<List<Integer>> finalFormula2 = tabularMethod2.removeRedundantImplicants();
        System.out.println("Минимизированная формула для P:");
        System.out.println(FormulaUtil.createDNF(sum, finalFormula2));
    }


}
