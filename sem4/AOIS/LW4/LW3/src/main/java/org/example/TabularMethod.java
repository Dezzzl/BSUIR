package org.example;

import org.example.CalculationMethod;
import org.example.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.FormulaType.PCNF;
import static org.example.FormulaType.PDNF;

public class TabularMethod {
    private String formula;
    private List<List<Integer>> perfectFormula;
    private List<List<Integer>> mergedFormula;
    private List<List<Integer>> table;

    public TabularMethod(String formula, List<List<Integer>> perfectFormula, List<List<Integer>> mergedFormula) {
        this.formula = formula;
        this.perfectFormula = perfectFormula;
        this.mergedFormula = mergedFormula;
        this.table = new ArrayList<>();
        formTable();
    }

    private void createTable() {
        for (int i = 0; i < this.mergedFormula.size(); i++) {
            List<Integer> row = new ArrayList<>(perfectFormula.size());
            this.table.add(row);
        }
    }

    public List<List<Integer>> getTable() {
        return table;
    }

    private void formTable() {
        createTable();
        for (int i = 0; i < this.table.size(); i++) {
            for (int j = 0; j < this.perfectFormula.size(); j++) {
                List<Integer> mergedSubformula = this.mergedFormula.get(i);
                List<Integer> perfectSubformula = this.perfectFormula.get(j);
                boolean isContained = checkIfContained(mergedSubformula, perfectSubformula);
                int fieldNumber = isContained ? 1 : 0;
                this.table.get(i).add(fieldNumber);
            }
        }
    }

    private boolean checkIfContained(List<Integer> mergedSubformula, List<Integer> perfectSubformula) {
        boolean isContained = true;
        for (int i = 0; i < mergedSubformula.size(); i++) {
            if (mergedSubformula.get(i) == 2) continue;
            if (!Objects.equals(mergedSubformula.get(i), perfectSubformula.get(i))) isContained = false;
        }
        return isContained;
    }

    public List<List<Integer>> removeRedundantImplicants() {
        List<List<Integer>> result = new ArrayList<>();
//        formTable();
        List<Integer> rowsToRemove = findRowsToRemove(this.table);
        for (int i = 0; i < this.table.size(); i++) {
            if (!rowsToRemove.contains(i)) {
                result.add(this.mergedFormula.get(i));
            }
        }
        return result;
    }

    public void showTable(FormulaType formulaType) {


        System.out.print(padRight("", 5));
        for (List<Integer> perfectSubformula : perfectFormula) {
            List<List<Integer>> perfectSList = List.of(perfectSubformula);
            if (formulaType == PDNF) {
                System.out.print(padCenter(FormulaUtil.createDNF(this.formula, perfectSList), FormulaUtil.createDNF(this.formula, perfectSList).length() + 5));
            } else {
                System.out.print(padCenter(FormulaUtil.createCNF(this.formula, perfectSList), 14));
            }
        }
        System.out.println();

        for (int i = 0; i < table.size(); i++) {
            List<Integer> mergedSubformula = mergedFormula.get(i);
            List<List<Integer>> mergedSList = List.of(mergedSubformula);
            if (formulaType == PDNF) {
                System.out.print(padCenter(FormulaUtil.createDNF(this.formula, mergedSList), 8));
            } else {
                System.out.print(padCenter(FormulaUtil.createCNF(this.formula, mergedSList), 8));
            }
            for (int j = 0; j < table.get(i).size(); j++) {
                if (formulaType == PDNF) {
                    System.out.print(padCenter(String.valueOf(table.get(i).get(j)), FormulaUtil.createDNF(this.formula, List.of(this.perfectFormula.get(j))).length() + 5)); // Добавляем отступы для каждого элемента
                }
                else System.out.print(padCenter(String.valueOf(table.get(i).get(j)), FormulaUtil.createCNF(this.formula, List.of(this.perfectFormula.get(j))).length() + 5));
            }
            System.out.println();
        }
    }


    private String padCenter(String s, int width) {
        return String.format("%-" + width + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    private String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }


    private static List<Integer> findRowsToRemove(List<List<Integer>> matrix) {
        List<Integer> rowsToRemove = new ArrayList<>();
        int numRows = matrix.size();
        int numCols = matrix.get(0).size();
        for (int row = 0; row < numRows; row++) {
            boolean hasOne = false;
            for (int col = 0; col < numCols; col++) {
                if (matrix.get(row).get(col) == 1) {
                    hasOne = true;
                    break;
                }
            }
            if (!hasOne) {
                rowsToRemove.add(row);
                continue;
            }
            List<Integer> tempRow = matrix.get(row);
            matrix.set(row, null);
            boolean columnHasOne;
            for (int col = 0; col < numCols; col++) {
                columnHasOne = false;
                for (int r = 0; r < numRows; r++) {
                    if (matrix.get(r) != null && matrix.get(r).get(col) == 1) {
                        columnHasOne = true;
                        break;
                    }
                }
                if (!columnHasOne) {
                    matrix.set(row, tempRow);
                    break;
                }
            }
            if (matrix.get(row) == null) {
                rowsToRemove.add(row);
            }
        }
        return rowsToRemove;
    }




//    public static void main(String[] args) {
//        String formula1 = "(!A/\\B/\\!C)\\/(!A/\\!B/\\C)\\/(!A/\\B/\\C)\\/(A/\\!B/\\C)";
//        System.out.println("Формула 1: " + formula1);
//        Parser parser1 = new Parser();
//        List<List<Integer>> perfectFormula = parser1.parse(formula1, PDNF);
//        CalculationMethod calculationMethod1 = new CalculationMethod(formula1, perfectFormula);
//        List<List<Integer>> merged = calculationMethod1.BondingStep();
//        TabularMethod tabularMethod = new TabularMethod(formula1, perfectFormula, merged);
//        tabularMethod.showTable(PDNF);
//        List<List<Integer>> lists = tabularMethod.removeRedundantImplicants();
//        System.out.println(lists);
//        System.out.println(FormulaUtil.createDNF(formula1, lists));
//        System.out.println(FormulaUtil.createCNF(formula1, lists));
//    }
}
