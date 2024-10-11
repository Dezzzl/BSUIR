package org.example;

import java.util.ArrayList;
import java.util.List;

public class FormulaUtil {
    public static String createDNF(String formula, List<List<Integer>> dnf){
        StringBuilder DNF = new StringBuilder();
        List<String> allAtomicFormulas = getAllAtomicFormulas(formula);
        for (int i = 0; i < dnf.size(); i++) {
            boolean brackets = true;
            int size = getRealLength(dnf.get(i));
            if (size==1)brackets = false;
            if (brackets) DNF.append(Constants.LEFT_BRACKET);
            for (int j = 0; j < dnf.get(i).size(); j++) {
                if (dnf.get(i).get(j)==0)DNF.append(Constants.NEGATION);
                if (dnf.get(i).get(j)!=2) {
                    DNF.append(allAtomicFormulas.get(j));
                    DNF.append(Constants.CONJUNCTION);
                }
            }
            DNF.delete(DNF.length() - 2, DNF.length());
            if (brackets) DNF.append(Constants.RIGHT_BRACKET);
            DNF.append(Constants.DISJUNCTION);
        }
        DNF.delete(DNF.length() - 2, DNF.length());
        return DNF.toString();
    }

    public static String createCNF(String formula, List<List<Integer>> dnf){
        StringBuilder CNF = new StringBuilder();
        List<String> allAtomicFormulas = getAllAtomicFormulas(formula);
        for (int i = 0; i < dnf.size(); i++) {
            boolean brackets = true;
            int size = getRealLength(dnf.get(i));
            if (size==1)brackets = false;
            if (brackets) CNF.append(Constants.LEFT_BRACKET);
            for (int j = 0; j < dnf.get(i).size(); j++) {
                if (dnf.get(i).get(j)==1)CNF.append(Constants.NEGATION);
                if (dnf.get(i).get(j)!=2) {
                    CNF.append(allAtomicFormulas.get(j));
                    CNF.append(Constants.DISJUNCTION);
                }
            }
            CNF.delete(CNF.length() - 2, CNF.length());
            if (brackets) CNF.append(Constants.RIGHT_BRACKET);
            CNF.append(Constants.CONJUNCTION);
        }
        CNF.delete(CNF.length() - 2, CNF.length());
        return CNF.toString();
    }

    private static int getRealLength(List<Integer> subformula){
        int length = 0;
        for (int i = 0; i < subformula.size(); i++) {
            if (subformula.get(i)!=2)length++;
        }
        return length;
    }

    public static List<String> getAllAtomicFormulas(String formula){
        List<String> result = new ArrayList<>();
        for (int i = 0; i < formula.length(); i++) {
            String symbol = Character.toString(formula.charAt(i));
            if (symbol.equals(Constants.RIGHT_BRACKET))break;
            if (Constants.symbols.contains(symbol))result.add(symbol);
        }
        return result;
    }
}
