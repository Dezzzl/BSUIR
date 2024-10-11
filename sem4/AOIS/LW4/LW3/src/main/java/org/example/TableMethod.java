package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.FormulaType.PCNF;
import static org.example.FormulaType.PDNF;

public class TableMethod {
    private final String formula;
    private final List<List<Integer>> numericFormula;
    private List<String> atomicFormulas = new ArrayList<>();
    private List<List<Integer>> truthTable = new ArrayList<>();
    private List<Integer> truthTableAnswer = new ArrayList<>();
    private FormulaType formulaType;
    private List<List<Integer>> carnoMap = new ArrayList<>();
    private static final List<String> ROWS = List.of("00", "01", "11", "10");
    private static final List<String> COLUMNS = List.of("0", "1");

    public TableMethod(String formula, List<List<Integer>> numericFormula, FormulaType formulaType) {
        this.formula = formula;
        this.numericFormula = numericFormula;
        this.atomicFormulas = FormulaUtil.getAllAtomicFormulas(this.formula);
        this.truthTable = createTruthTable(this.atomicFormulas.size());
        this.formulaType = formulaType;
        this.truthTableAnswer = createTruthTableResult(this.numericFormula, this.truthTable);
        createCarnoMap();
    }

    public String solveTheCarnoMap() {
        return null;
    }

    public List<List<Integer>> createTruthTable(int numVariables) {
        List<List<Integer>> truthTable = new ArrayList<>();
        generateTruthTableRows(new ArrayList<>(), numVariables, truthTable);
        return truthTable;
    }

    private void generateTruthTableRows(List<Integer> currentRow, int numVariables, List<List<Integer>> truthTable) {
        if (currentRow.size() == numVariables) {
            truthTable.add(new ArrayList<>(currentRow));
            return;
        }

        currentRow.add(0);
        generateTruthTableRows(currentRow, numVariables, truthTable);
        currentRow.remove(currentRow.size() - 1);

        currentRow.add(1);
        generateTruthTableRows(currentRow, numVariables, truthTable);
        currentRow.remove(currentRow.size() - 1);
    }


    public String getFormula() {
        return formula;
    }

    public List<List<Integer>> getNumericFormula() {
        return numericFormula;
    }

    public List<String> getAtomicFormulas() {
        return atomicFormulas;
    }

    public List<List<Integer>> getTruthTable() {
        return truthTable;
    }

    public List<Integer> getTruthTableAnswer() {
        return truthTableAnswer;
    }

    public FormulaType getFormulaType() {
        return formulaType;
    }

    private List<Integer> createTruthTableResult(List<List<Integer>> formula, List<List<Integer>> truthTable) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < truthTable.size(); i++) {
            List<Integer> row = new ArrayList<>();
            boolean containsRow = formula.contains(truthTable.get(i));
            if (this.formulaType == PDNF) {
                result.add(containsRow ? 1 : 0);
            } else {
                result.add(containsRow ? 0 : 1);
            }
        }
        return result;
    }

    private void createCarnoMap() {
        int rows = this.atomicFormulas.size() / 2;
        int columns = this.atomicFormulas.size() - rows;
        for (int i = 0; i < rows * 2; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < columns * 2; j++) {
                row.add(0);  // Заполняем все значения нулями изначально
            }
            this.carnoMap.add(row);
        }
        List<List<Integer>> tableForRows = createTruthTable(rows);
        List<List<Integer>> tableForColumns = createTruthTable(columns);
        reverseSecondHalf(tableForRows);
        reverseSecondHalf(tableForColumns);
        for (int i = 0; i < rows * 2; i++) {
            for (int j = 0; j < columns * 2; j++) {
                List<Integer> setForRow = tableForRows.get(i);
                List<Integer> setForColumn = tableForColumns.get(j);
                List<Integer> combinedSet = new ArrayList<>();
                combinedSet.addAll(setForRow);
                combinedSet.addAll(setForColumn);
                int index = findIndex(this.truthTable, combinedSet);
                int valueForCarnoTable = this.truthTableAnswer.get(index);
                this.carnoMap.get(i).set(j, valueForCarnoTable);
            }

        }
    }

    public static void reverseSecondHalf(List<List<Integer>> listOfLists) {
        int halfSize = listOfLists.size() / 2;
        List<List<Integer>> sublistToReverse = listOfLists.subList(halfSize, listOfLists.size());
        Collections.reverse(sublistToReverse);
    }


    private int findIndex(List<List<Integer>> listOfLists, List<Integer> targetList) {
        for (int i = 0; i < listOfLists.size(); i++) {
            if (listOfLists.get(i).equals(targetList)) {
                return i;
            }
        }
        return -1; // Если не найден
    }


    private List<List<List<Integer>>> getAll4Squares() {
        int i = 0, j = 1, k = 2, l = 3;
        List<List<List<Integer>>> result = new ArrayList<>();
        for (int p = 0; p < this.carnoMap.size(); p++) {
            List<Integer> row = this.carnoMap.get(p);
            if (row.get(i) == 1 && row.get(j) == 1 && row.get(k) == 1 && row.get(l) == 1) {
                List<Integer> first = List.of(p, i);
                List<Integer> second = List.of(p, j);
                List<Integer> third = List.of(p, k);
                List<Integer> fourth = List.of(p, l);
                result.add(List.of(first, second, third, fourth));
            }
        }
        return result;
    }

    private List<List<List<Integer>>> getAllQuadrants() {
        int i = 0, j = 1;
        List<List<List<Integer>>> result = new ArrayList<>();
        List<Integer> firstRow = this.carnoMap.get(i);
        List<Integer> secondRow = this.carnoMap.get(j);
        for (int k = 0; k < this.carnoMap.get(0).size() - 1; k++) {
            if (firstRow.get(k) == 1 && firstRow.get(k + 1) == 1 && secondRow.get(k) == 1 && secondRow.get(k + 1) == 1) {
                List<Integer> first = List.of(i, k);
                List<Integer> second = List.of(i, k + 1);
                List<Integer> third = List.of(j, k);
                List<Integer> fourth = List.of(j, k + 1);
                result.add(List.of(first, second, third, fourth));
            }
        }
        if (firstRow.get(0) == 1 && firstRow.get(3) == 1 && secondRow.get(0) == 1 && secondRow.get(3) == 1) {
            List<Integer> first = List.of(0, 0);
            List<Integer> second = List.of(0, 3);
            List<Integer> third = List.of(1, 0);
            List<Integer> fourth = List.of(1, 3);
            result.add(List.of(first, second, third, fourth));
        }
        return result;
    }

    private List<List<List<Integer>>> getAll2SquaresVertical() {
        int i = 0, j = 1;
        List<List<List<Integer>>> result = new ArrayList<>();
        List<Integer> firstRow = this.carnoMap.get(i);
        List<Integer> secondRow = this.carnoMap.get(j);
        for (int p = 0; p < this.carnoMap.get(i).size(); p++) {
            if (firstRow.get(p) == 1 && secondRow.get(p) == 1) {
                List<Integer> first = List.of(i, p);
                List<Integer> second = List.of(j, p);
                result.add(List.of(first, second));
            }
        }
        return result;
    }

    private List<List<List<Integer>>> getAll2SquaresHorizontal() {
        List<List<List<Integer>>> result = new ArrayList<>();
        for (int p = 0; p < this.carnoMap.size(); p++) {
            for (int k = 0; k < this.carnoMap.get(0).size() - 1; k++)
                if (this.carnoMap.get(p).get(k) == 1 && this.carnoMap.get(p).get(k + 1) == 1) {
                    List<Integer> first = List.of(p, k);
                    List<Integer> second = List.of(p, k + 1);
                    result.add(List.of(first, second));
                }
        }
        if (this.carnoMap.get(0).get(0) == 1 && this.carnoMap.get(0).get(3) == 1) {
            List<Integer> first = List.of(0, 0);
            List<Integer> second = List.of(0, 3);
            result.add(List.of(first, second));
        }
        if (this.carnoMap.get(1).get(0) == 1 && this.carnoMap.get(1).get(3) == 1) {
            List<Integer> first = List.of(1, 0);
            List<Integer> second = List.of(1, 3);
            result.add(List.of(first, second));
        }
        return result;
    }

    private List<List<List<Integer>>> getAllOneSquare() {
        List<List<List<Integer>>> result = new ArrayList<>();
        for (int p = 0; p < this.carnoMap.size(); p++) {
            for (int i = 0; i < this.carnoMap.get(0).size(); i++) {
                if (this.carnoMap.get(p).get(i) == 1) {
                    List<Integer> first = List.of(p, i);
                    result.add(List.of(first));
                }
            }
        }
        return result;
    }

    private List<List<List<Integer>>> getAllImplicants() {
        List<List<List<Integer>>> result = new ArrayList<>(getAll4Squares());
        result.addAll(getAllQuadrants());
        List<List<List<Integer>>> allTwoSquares = checkForTwoSquare(result);
        List<List<List<Integer>>> allOneSquares = checkForOneSquare(result);
        result.addAll(allOneSquares);
        return result;
    }

    private List<List<List<Integer>>> checkForTwoSquare(List<List<List<Integer>>> squares) {
        List<List<List<Integer>>> result = new ArrayList<>();
        List<List<List<Integer>>> allTwoSquares = getAll2SquaresHorizontal();
        allTwoSquares.addAll(getAll2SquaresVertical());
        List<List<List<Integer>>>squaresForSearch = new ArrayList<>(squares);
//        squaresForSearch.addAll(allTwoSquares);
        for (List<List<Integer>> twoSquare : allTwoSquares) {
            boolean firstValue = true;
            boolean secondValue = true;
            for (int i = 0; i < squaresForSearch.size(); i++) {
                if (squaresForSearch.get(i).equals(twoSquare)) continue;
                for (List<Integer> coordiante : squaresForSearch.get(i)) {
                    if (coordiante.equals(twoSquare.get(0))) firstValue = false;
                    if (coordiante.equals(twoSquare.get(1))) secondValue = false;
                }
            }
            if (!firstValue && !secondValue) ;
//            else if (firstValue!=secondValue){
//                allTwoSquares.add(twoSquare);
//            }
            else if (firstValue&&secondValue){
                result.add(twoSquare);
                squares.add(twoSquare);
                squaresForSearch.add(twoSquare);
            }
        }

        for (List<List<Integer>> twoSquare : allTwoSquares) {
            boolean firstValue = true;
            boolean secondValue = true;
            for (int i = 0; i < squaresForSearch.size(); i++) {
                for (List<Integer> coordiante : squaresForSearch.get(i)) {
                    if (coordiante.equals(twoSquare.get(0))) firstValue = false;
                    if (coordiante.equals(twoSquare.get(1))) secondValue = false;
                }
            }
            if (!firstValue && !secondValue) ;
//            else if (firstValue!=secondValue){
//                allTwoSquares.add(twoSquare);
//            }
            else {
                result.add(twoSquare);
                squares.add(twoSquare);
                squaresForSearch.add(twoSquare);
            }
        }

        return result;
    }

    private List<List<List<Integer>>> checkForOneSquare(List<List<List<Integer>>> squares) {
        List<List<List<Integer>>> result = new ArrayList<>();
        List<List<List<Integer>>> allOneSquares = getAllOneSquare();
        for (List<List<Integer>> twoSquare : allOneSquares) {
            boolean firstValue = true;
            for (int i = 0; i < squares.size(); i++) {
                for (List<Integer> coordiante : squares.get(i)) {
                    if (coordiante.equals(twoSquare.get(0))) firstValue = false;
                }
            }
            if (firstValue) result.add(twoSquare);
        }
        return result;
    }

    private List<List<String>> generateStrImplicants(List<List<List<Integer>>> implicants) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < implicants.size(); i++) {
            List<String> strImplicants = new ArrayList<>();
            for (List<Integer> implicant : implicants.get(i)) {
                String strImplicant = COLUMNS.get(implicant.get(0)) + ROWS.get(implicant.get(1));
                strImplicants.add(strImplicant);
            }
            result.add(strImplicants);
        }
        return result;
    }

    private List<List<Integer>> formAnswer(List<List<String>> strImplicants) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < strImplicants.size(); i++) {
            int pointer = 0;
            List<Integer> implicant = new ArrayList<>();
            while (pointer != 3) {
                boolean flag = true;
                for (int j = 0; j < strImplicants.get(i).size(); j++) {
                    if (strImplicants.get(i).get(j).charAt(pointer) != strImplicants.get(i).get(0).charAt(pointer))
                        flag = false;
                }
                if (!flag) implicant.add(2);
                else if (strImplicants.get(i).get(0).charAt(pointer) == '1') implicant.add(1);
                else implicant.add(0);
                pointer++;
            }
            result.add(implicant);
        }
        return result;
    }

    private void reverseCarnoMap() {
        for (int i = 0; i < this.carnoMap.size(); i++) {
            for (int j = 0; j < this.carnoMap.get(i).size(); j++) {
                if (this.carnoMap.get(i).get(j) == 1) this.carnoMap.get(i).set(j, 0);
                else this.carnoMap.get(i).set(j, 1);
            }
        }
    }

    public String createDNF() {
        List<List<List<Integer>>> allImplicants = getAllImplicants();
        List<List<String>> strImplicants = generateStrImplicants(allImplicants);
        List<List<Integer>> implicants = formAnswer(strImplicants);
        return FormulaUtil.createDNF(this.formula, implicants);
    }

    public String createCNF() {
        reverseCarnoMap();
        List<List<List<Integer>>> allImplicants = getAllImplicants();
        List<List<String>> strImplicants = generateStrImplicants(allImplicants);
        List<List<Integer>> implicants = formAnswer(strImplicants);
        reverseCarnoMap();
        return FormulaUtil.createCNF(this.formula, implicants);
    }

    public String Minimize(FormulaType formulaType) {
        if (formulaType == PCNF)reverseCarnoMap();
        List<List<List<Integer>>> allImplicants = getAllImplicants();
        List<List<String>> strImplicants = generateStrImplicants(allImplicants);
        List<List<Integer>> implicants = formAnswer(strImplicants);
        if (formulaType == PDNF)
            return FormulaUtil.createDNF(this.formula, implicants);
        else {reverseCarnoMap();
            return FormulaUtil.createCNF(this.formula, implicants);}
    }

    public void printCarnoTable() {
        System.out.print("   ");
        for (String row : ROWS) System.out.print(row + " ");
        System.out.println();
        for (int i = 0; i < this.carnoMap.size(); i++) {
            System.out.print(COLUMNS.get(i) + "  ");
            for (int j = 0; j < this.carnoMap.get(i).size(); j++) {
                System.out.print(this.carnoMap.get(i).get(j) + "  ");
            }
            System.out.println();
        }
    }

    public List<List<Integer>> getCarnoMap() {
        return carnoMap;
    }

//    public static void main(String[] args) {
//        String formula1 = "(!A/\\B/\\!C)\\/(A/\\!B/\\!C)\\/(A/\\B/\\!C)";
//        System.out.println("Формула 1: " + formula1);
//        Parser parser1 = new Parser();
//        List<List<Integer>> perfectFormula = parser1.parse(formula1, PDNF);
//        System.out.println(perfectFormula);
//        TableMethod tableMethod = new TableMethod(formula1, perfectFormula, PDNF);
//        List<Integer> truthTableAnswer1 = tableMethod.truthTableAnswer;
//        System.out.println(truthTableAnswer1);
//        System.out.println(tableMethod.Minimize(PDNF));
//        tableMethod.printCarnoTable();
//    }

}
