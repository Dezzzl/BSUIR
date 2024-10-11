package org.example.source;

import org.example.Util.Constants;
import org.example.Util.FormulaUtil;
import org.example.Util.Operations;

import java.util.*;

public class TruthTable {
    private final String formula;

    private final List<String> atomicFormulas;

    private final HashMap<String, List<Integer>> subformulas = new LinkedHashMap<>();

    private List<List<Integer>> table = new ArrayList<>();

    public TruthTable(Set<String> atomicFormulas, String formula) {
        this.formula = formula;
        this.atomicFormulas = new ArrayList<>(atomicFormulas);
        initializeTruthTable();
    }

    private void evaluateFormula() {
        Set<String> atomicFormulas = new HashSet<>();
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < this.formula.length(); i++) {
            String symbol = Character.toString(this.formula.charAt(i));
            if (Constants.numbers.contains(symbol) || Constants.symbols.contains(symbol)) {
                stack.push(symbol);
                continue;
            }
            if (symbol.equals(Constants.LEFT_BRACKET)) {
                stack.push(symbol);
                continue;
            }
            if (symbol.equals(Constants.EQUIVALENCE)
                || symbol.equals(Constants.DISJUNCTION)
                || symbol.equals(Constants.CONJUNCTION)
                || symbol.equals(Constants.IMPLICATION)
                || symbol.equals(Constants.NEGATION)) {
                stack.push(symbol);
                continue;
            }
            if (symbol.equals(Constants.RIGHT_BRACKET)) {
                StringBuilder insideBrackets = new StringBuilder();
                String operator = "";
                List<String> subformulas = new ArrayList<>();
                StringBuilder subformula = new StringBuilder();
                while (!stack.isEmpty() && !stack.peek().equals(Constants.LEFT_BRACKET)) {
                    if (Operations.getAllOperators().contains(stack.peek())) {
                        operator = stack.peek();
                        if (!subformula.isEmpty()) subformulas.add(subformula.toString());
                        subformula = new StringBuilder();
                    } else subformula.insert(0, stack.peek());
                    insideBrackets.insert(0, stack.pop());
                }
                if (!subformula.isEmpty()) subformulas.add(subformula.toString());
                insideBrackets.insert(0, stack.pop());
                insideBrackets.append(symbol);
                stack.push(insideBrackets.toString());
                calculate(insideBrackets.toString(), subformulas, operator);
                continue;
            }
        }
    }

    private void initializeTruthTable() {
        int numRows = (int) Math.pow(2, atomicFormulas.size());
        int numColumns = atomicFormulas.size();

        for (int row = 0; row < numRows; row++) {
            List<Integer> currentRow = new ArrayList<>();
            int temp = row;
            for (int col = numColumns - 1; col >= 0; col--) {
                currentRow.add(0, temp % 2);
                temp /= 2;
            }
            table.add(currentRow);
        }

        for (int col = 0; col < numColumns; col++) {
            List<Integer> columnValues = new ArrayList<>();
            for (List<Integer> row : table) {
                columnValues.add(row.get(col));
            }
            subformulas.put(atomicFormulas.get(col), columnValues);
        }

        evaluateFormula();
    }


    public void displayTruthTable() {
        System.out.println("Таблица истинности для формулы: " + FormulaUtil.reverseExpressionTransformation(this.formula));
        for (String formula : this.subformulas.keySet()) {
            System.out.print(FormulaUtil.reverseExpressionTransformation(formula) + " | ");
        }
        System.out.println();


        for (int row = 0; row < table.size(); row++) {
            for (Map.Entry<String, List<Integer>> entry : subformulas.entrySet()) {
                String valueString = String.valueOf(entry.getValue().get(row));
                int numSpaces = FormulaUtil.reverseExpressionTransformation(entry.getKey()).length() - 1;
                System.out.print(" ".repeat(numSpaces) + valueString + " | ");
            }
            System.out.println();
        }
    }


    private void calculate(String formula, List<String> subfromulas, String operator) {
        List<Integer> resultRow = new ArrayList<>();
        if (subfromulas.size() == 1) {
            List<Integer> row = this.subformulas.get(subfromulas.get(0));
            for (int i = 0; i < row.size(); i++) {
                boolean operate = Operations.Operate(getBooleanValue(row.get(i)), operator);
                resultRow.add(getIntegerValue(operate));
            }
        } else {
            List<Integer> firstRow = this.subformulas.get(subfromulas.get(1));
            List<Integer> secondRow = this.subformulas.get(subfromulas.get(0));
            for (int i = 0; i < firstRow.size(); i++) {
                boolean operate = Operations.Operate(getBooleanValue(firstRow.get(i)), getBooleanValue(secondRow.get(i)), operator);
                resultRow.add(getIntegerValue(operate));
            }
        }
        this.subformulas.put(formula, resultRow);
    }

    private boolean getBooleanValue(int intValue) {
        return intValue == 1;
    }

    private int getIntegerValue(boolean booleanValue) {
        return booleanValue ? 1 : 0;
    }


    public String getFormula() {
        return formula;
    }

    public List<String> getAtomicFormulas() {
        return atomicFormulas;
    }

    public HashMap<String, List<Integer>> getSubformulas() {
        return subformulas;
    }

    public List<List<Integer>> getTable() {
        return table;
    }
}
