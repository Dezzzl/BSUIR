package org.example.Util;

import org.example.Util.Constants;

import java.util.List;

public class Operations {
    public static boolean Operate(boolean a, boolean b, String operator)
    {
        return switch (operator) {
            case "&" -> a && b;
            case "|" -> a || b;
            case ">" -> (!a) || b;
            case "~" -> a == b;
            default -> false;
        };
    }
    public static boolean Operate(boolean a, String operator)
    {
        if (operator.equals("!")) {
            return !a;
        }
        return false;
    }

    public static List<String> getBinaryOperators(){
        return List.of(Constants.DISJUNCTION, Constants.EQUIVALENCE, Constants.CONJUNCTION, Constants.IMPLICATION);
    }

    public static List<String> getAllOperators(){
        return List.of(Constants.DISJUNCTION, Constants.EQUIVALENCE, Constants.CONJUNCTION, Constants.IMPLICATION, Constants.NEGATION);
    }
}
