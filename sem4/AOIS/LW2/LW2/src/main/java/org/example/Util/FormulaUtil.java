package org.example.Util;

import java.util.List;

public class FormulaUtil {
    public static String expressionTransformation(String inputExpression) {
        List<String> replacementExpressions = List.of("->", "\\/", "/\\");
        for (String expression : replacementExpressions) {
            switch (expression) {
                case "->":
                    inputExpression = inputExpression.replace(expression, ">");
                    break;
                case "\\/":
                    inputExpression = inputExpression.replace(expression, "|");
                    break;
                case "/\\":
                    inputExpression = inputExpression.replace(expression, "&");
                    break;
                default:
                    break;
            }
        }
        return inputExpression;
    }

    public static String reverseExpressionTransformation(String transformedExpression) {
        transformedExpression = transformedExpression.replace(">", "->");
        transformedExpression = transformedExpression.replace("|", "\\/");
        transformedExpression = transformedExpression.replace("&", "/\\");
        return transformedExpression;
    }

}
