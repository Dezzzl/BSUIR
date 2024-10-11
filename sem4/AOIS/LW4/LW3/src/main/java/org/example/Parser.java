package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.FormulaType.*;

public class Parser {
    public List<List<Integer>> parse(String formula, FormulaType formulaType){
        List<Integer> subformula = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < formula.length(); i++) {
            String symbol = Character.toString(formula.charAt(i));
            if (symbol.equals(Constants.LEFT_BRACKET)) subformula = new ArrayList<>();
            else if (symbol.equals(Constants.RIGHT_BRACKET)) result.add(subformula);
            else if (symbol.equals(Constants.NEGATION)){
                i++;
                if(formulaType.equals(PDNF)) subformula.add(0);
                else subformula.add(1);
            }
            else if (Constants.symbols.contains(symbol)){
                if(formulaType.equals(PDNF)) subformula.add(1);
                else subformula.add(0);
            }
        }
        return result;
    }

//    public static void main(String[] args) {
//        String formula = "(A/\\B/\\C)\\/(!A/\\!B/\\C)";
//        Parser parser = new Parser();
//        System.out.println(parser.parse(formula, PDNF));
//        System.out.println(5/2);
//    }
}
