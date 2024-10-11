package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.FormulaType.PCNF;
import static org.example.FormulaType.PDNF;

public class CalculationMethod {
    //   private final List<List<Integer>> numericFormula;
    private final String formula;
    private final List<List<Integer>> numericFormula;
    private Set<Integer> usedFormulas = new HashSet<>();

    public CalculationMethod(String formula, List<List<Integer>> numericFormula) {
        this.numericFormula = numericFormula;
        this.formula = formula;
    }

    public String getDnfWithCalculationMethod(){
        List<List<Integer>> numericFormula = BondingStep();
        List<List<Integer>> numericFormulaWithoutRedundantImplicants = removeRedundantImplicants(numericFormula);
        return FormulaUtil.createDNF(this.formula, numericFormulaWithoutRedundantImplicants);
    }

    public String getCnfWithCalculationMethod(){
        List<List<Integer>> numericFormula = BondingStep();
        List<List<Integer>> numericFormulaWithoutRedundantImplicants = removeRedundantImplicants(numericFormula);
        return FormulaUtil.createCNF(this.formula, numericFormulaWithoutRedundantImplicants);
    }

    public List<List<Integer>> BondingStep() {
        int countOfVariables = this.numericFormula.get(0).size();
        List<List<Integer>>allUnusedFormulas = new ArrayList<>();
        List<List<Integer>> currentFormula = new ArrayList<>(this.numericFormula.size());
        List<List<Integer>> buffFormula = new ArrayList<>(this.numericFormula.size());
        for (List<Integer> sublist : this.numericFormula) {
            currentFormula.add(new ArrayList<>(sublist));
        }
        for (List<Integer> sublist : this.numericFormula) {
            buffFormula.add(new ArrayList<>(sublist));
        }
        while (countOfVariables > 1) {
            List<List<Integer>> allCombinations = getAllCombinations(countOfVariables, --countOfVariables);
            List<List<Integer>> mergedFormula = new ArrayList<>();
            for (List<Integer> combination : allCombinations) {
                currentFormula = new ArrayList<>();
                for (List<Integer> sublist : buffFormula) {
                    currentFormula.add(new ArrayList<>(sublist));
                }
                List<List<Integer>> mergedSubformula = merge(combination, currentFormula);
                mergedFormula.addAll(mergedSubformula);
            }
            List<List<Integer>> unusedFormulas = getUnusedFormulas(currentFormula);
            allUnusedFormulas.addAll(unusedFormulas);
            this.usedFormulas = new HashSet<>();
            currentFormula = mergedFormula;
            buffFormula = mergedFormula;
        }

        currentFormula.addAll(allUnusedFormulas);
        Set<List<Integer>> uniqueSublists = new HashSet<>(currentFormula);
        return new ArrayList<>(uniqueSublists);
    }


    private List<List<Integer>> getUnusedFormulas(List<List<Integer>> formulas){
        List<List<Integer>> unusedFormulas = new ArrayList<>();
        for(int i = 0; i< formulas.size(); i++){
            if (!this.usedFormulas.contains(i))unusedFormulas.add(formulas.get(i));
        }
        return  unusedFormulas;
    }

    private  List<List<Integer>> merge(List<Integer> combination, List<List<Integer>> currentFormula) {
        List<List<Integer>> result = new ArrayList<>();
        List<List<Integer>> bufferFormula = new ArrayList<>();
        for (List<Integer> sublist : currentFormula) {
            bufferFormula.add(new ArrayList<>(sublist));
        }
        for (int i = 0; i < currentFormula.size(); i++) {
            for (int j = i + 1; j < currentFormula.size(); j++) {
                boolean flag = true;
                List<Integer> sublist1 = bufferFormula.get(i);
                List<Integer> sublist2 = bufferFormula.get(j);
                for (int index : combination) {
                    int realIndex1 = getRealIndex(index, sublist1);
                    int realIndex2 = getRealIndex(index, sublist2);
//                    if (sublist1.get(realIndex1).equals(2) || sublist2.get(realIndex2).equals(2)) {
//                        flag = false;
//                        break;
//                    }
                    if (realIndex1!=realIndex2) {
                        flag = false;
                        break;
                    }
                    if (!sublist1.get(realIndex1).equals(sublist2.get(realIndex2))) {
                        flag = false;
                        break;
                    }
                    if (sublist1.get(getRealIndex(getTheRemainingIndex(combination), sublist1))+sublist2.get(getRealIndex(getTheRemainingIndex(combination), sublist2))!=1){
                        flag = false;
                        break;
                    }
                    if (getRealIndex(getTheRemainingIndex(combination), sublist1)!=getRealIndex(getTheRemainingIndex(combination), sublist2)){
                        flag = false;
                        break;
                    }

                }
                if (flag) {
                    int remainingIndex = getTheRemainingIndex(combination);
                    int realRemainingIndex1 = getRealIndex(remainingIndex, sublist1);
                    int realRemainingIndex2 = getRealIndex(remainingIndex, sublist2);
                    currentFormula.get(i).set(realRemainingIndex1, 2);
                    currentFormula.get(j).set(realRemainingIndex2, 2);
                    result.add(new ArrayList<>(currentFormula.get(i)));
                    this.usedFormulas.add(i);
                    this.usedFormulas.add(j);
                }
            }
        }
        return result;
    }


    private static int getTheRemainingIndex(List<Integer> combinations) {
        int maxElement = combinations.size();
        int counter = 0;
        boolean flag = true;
        while (counter != maxElement) {
            for (int index : combinations) {
                if (counter == index) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                counter++;
                flag = true;
            } else break;
        }
        return counter;
    }

    private static int getRealIndex(int index, List<Integer> sublist) {
        int non2Count = 0;
        for (int i = 0; i < sublist.size(); i++) {
            if (sublist.get(i) != 2) {
                if (non2Count == index) {
                    return i;
                }
                non2Count++;
            }
        }
        return sublist.size();
    }



    private static List<List<Integer>> getAllCombinations(int size, int combinationSize) {
        List<List<Integer>> result = new ArrayList<>();
        if (size <= 0 || combinationSize <= 0 || combinationSize > size) {
            return result;
        }
        boolean[] used = new boolean[size];
        List<Integer> current = new ArrayList<>();
        generateCombinations(result, current, used, size, 0, combinationSize);
        return result;
    }

    private static void generateCombinations(List<List<Integer>> result, List<Integer> current, boolean[] used, int size, int index, int combinationSize) {
        if (current.size() == combinationSize) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = index; i < size; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(i);
                generateCombinations(result, current, used, size, i + 1, combinationSize);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    public static List<List<Integer>> removeRedundantImplicants(List<List<Integer>> implicants) {
        List<List<Integer>> reducedImplicants = new ArrayList<>(implicants);

        for (int i = 0; i < reducedImplicants.size(); i++) {
            if (!isRedundant(reducedImplicants.get(i), implicants, i)) {
                continue;
            }
            reducedImplicants.remove(i);
            i--;
        }

        return reducedImplicants;
    }

    private static boolean isRedundant(List<Integer> implicant, List<List<Integer>> implicants, int currentIndex) {
        List<List<Integer>> resultImplicant = new ArrayList<>();
        for (int i = 0; i < implicants.size(); i++) {
            if (i != currentIndex) {
                List<Integer> covers = covers(implicant, implicants.get(i));
                resultImplicant.add(covers);
            }
        }
        for (int i=0; i<resultImplicant.size()-1; i++){
            for (int j = i+1; j<resultImplicant.size(); j++){
                if (resultImplicant.get(i).get(0)+resultImplicant.get(j).get(0)==1||resultImplicant.get(i).get(1)+resultImplicant.get(j).get(1)==1||resultImplicant.get(i).get(2)+resultImplicant.get(j).get(2)==1)return true;
            }
        }
        return false;
    }

    private static List<Integer> covers(List<Integer> implicant, List<Integer> other) {
        List<Integer> resultOtherImplicant = new ArrayList<>();
        for (int i = 0; i < implicant.size(); i++) {
            if (implicant.get(i) != 2 && implicant.get(i) == other.get(i)) {
                resultOtherImplicant.add(2);
            }
            else resultOtherImplicant.add(other.get(i));
        }
        return resultOtherImplicant;
    }




//    public static void main(String[] args) {
//        String formula1 = "(!A/\\B/\\C)\\/(A/\\!B/\\!C)\\/(A/\\!B/\\C)\\/(A/\\B/\\!C)\\/(A/\\B/\\C)";
//        String formula2 = "(A\\/B\\/C)/\\(A\\/B\\/!C)/\\(A\\/!B\\/!C)/\\(!A\\/B\\/!C)/\\(!A\\/!B\\/!C)";
////        String formula = "(!A/\\B/\\C)\\/(A/\\!B/\\!C)\\/(A/\\!B/\\C)\\/(A/\\B/\\!C)\\/(A/\\B/\\C)";
//        System.out.println("Формула 1: "+formula1);
//        Parser parser1 = new Parser();
//        Parser parser2 = new Parser();
////        System.out.println(parser1.parse(formula1, PDNF));
////        System.out.println(parser2.parse(formula1, PCNF));
//        CalculationMethod calculationMethod1 = new CalculationMethod(formula1, parser1.parse(formula1, PDNF));
//        CalculationMethod calculationMethod2 = new CalculationMethod(formula2, parser2.parse(formula2, PCNF));
//        String dnf = calculationMethod1.getDnfWithCalculationMethod();
//        String cnf = calculationMethod2.getCnfWithCalculationMethod();
//        System.out.println(dnf);
//        System.out.println(cnf);
//    }
}
