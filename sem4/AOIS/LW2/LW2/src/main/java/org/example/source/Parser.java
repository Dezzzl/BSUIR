package org.example.source;

import org.example.Exceptions.NotFormulaException;
import org.example.Util.Constants;
import org.example.Util.FormulaUtil;
import org.example.Util.Operations;

import java.util.*;

public class Parser {
    public TruthTable isValid(String expression) throws NotFormulaException {
        Set<String> atomicFormulas = new HashSet<>();
        try {
            checkIfAtomicFormula(expression);
            atomicFormulas.add(expression);
            return new TruthTable(atomicFormulas, expression);
        } catch (NotFormulaException ignored) {

        }
        String preparedExpression = FormulaUtil.expressionTransformation(expression);
        String symbol = Character.toString(expression.charAt(0));
        StringBuilder buffer = new StringBuilder();
        if (!(preparedExpression.charAt(0) == '(' || Constants.symbols.contains(symbol)))
            throw new NotFormulaException();
        if (Constants.symbols.contains(symbol)) buffer.append(symbol);
        Stack<String> stack = new Stack<>();
        stack.push(symbol);
        for (int i = 1; i < preparedExpression.length(); i++) {
            symbol = Character.toString(preparedExpression.charAt(i));
            String previousSymbol = Character.toString(preparedExpression.charAt(i - 1));
            if (Constants.numbers.contains(symbol) || Constants.symbols.contains(symbol)) {
                stack.push(symbol);
                buffer.append(symbol);
                checkIfAtomicFormula(buffer.toString());
                checkPastCharacterForAlphabet(previousSymbol);
                continue;
            }
            if (!buffer.isEmpty()) {
                atomicFormulas.add(buffer.toString());
                buffer = new StringBuilder();
            }
            if (symbol.equals(Constants.LEFT_BRACKET)) {
                checkPastCharacterForLeftBracket(previousSymbol);
                stack.push(symbol);
                continue;
            }
            if (symbol.equals(Constants.EQUIVALENCE)
                || symbol.equals(Constants.DISJUNCTION)
                || symbol.equals(Constants.CONJUNCTION)
                || symbol.equals(Constants.IMPLICATION)) {
                checkPastCharacterForBinaryOperators(previousSymbol);
                stack.push(symbol);
                continue;
            }
            if (symbol.equals(Constants.NEGATION)) {
                stack.push(symbol);
                checkPastCharacterForNegation(previousSymbol);
                continue;
            }
            if (symbol.equals(Constants.RIGHT_BRACKET)) {
                checkPastCharacterForRightBracket(previousSymbol);
                StringBuilder insideBrackets = new StringBuilder();
                Stack<String> copy = new Stack<>();
                copy.addAll(stack);
                checkSubformula(copy, atomicFormulas);
                while (!stack.isEmpty() && !stack.peek().equals(Constants.LEFT_BRACKET)) {
                    insideBrackets.insert(0, stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new NotFormulaException();
                }
                insideBrackets.insert(0, stack.pop());
                insideBrackets.append(insideBrackets).append(symbol);
                continue;
            } else throw new NotFormulaException();
        }
        if (!stack.isEmpty()) throw new NotFormulaException();
        return new TruthTable(atomicFormulas, preparedExpression);
    }

    private void checkIfAtomicFormula(String expression) throws NotFormulaException {
        String firstSymbol = Character.toString(expression.charAt(0));
        if (!Constants.symbols.contains(firstSymbol)) {
            throw new NotFormulaException();
        }
        for (int i = 1; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (i == 1 && ch == '0') {
                throw new NotFormulaException();
            }
            if (!Constants.numbers.contains(Character.toString(expression.charAt(i)))) {
                throw new NotFormulaException();
            }
        }
    }

    private static void checkPastCharacterForLeftBracket(String symbol) throws NotFormulaException {
        if (Constants.numbers.contains(symbol)) throw new NotFormulaException();
        if (Constants.symbols.contains(symbol)) throw new NotFormulaException();
        if (symbol.equals(Constants.RIGHT_BRACKET)) throw new NotFormulaException();
    }

    private static void checkPastCharacterForRightBracket(String symbol) throws NotFormulaException {
        if (
                symbol.equals(Constants.EQUIVALENCE)
                || symbol.equals(Constants.DISJUNCTION)
                || symbol.equals(Constants.CONJUNCTION)
                || symbol.equals(Constants.IMPLICATION)
                || symbol.equals(Constants.NEGATION)
        ) throw new NotFormulaException();
    }

    private static void checkPastCharacterForBinaryOperators(String symbol) throws NotFormulaException {
        if (
                symbol.equals(Constants.EQUIVALENCE)
                || symbol.equals(Constants.DISJUNCTION)
                || symbol.equals(Constants.CONJUNCTION)
                || symbol.equals(Constants.IMPLICATION)
                || symbol.equals(Constants.NEGATION)
        ) throw new NotFormulaException();
        if (symbol.equals(Constants.LEFT_BRACKET)) throw new NotFormulaException();
    }

    private static void checkPastCharacterForNegation(String symbol) throws NotFormulaException {
        if (symbol.equals(Constants.RIGHT_BRACKET)) throw new NotFormulaException();
        if (
                symbol.equals(Constants.EQUIVALENCE)
                || symbol.equals(Constants.DISJUNCTION)
                || symbol.equals(Constants.CONJUNCTION)
                || symbol.equals(Constants.IMPLICATION)
                || symbol.equals(Constants.NEGATION)
        ) throw new NotFormulaException();
    }

    void checkSubformula(Stack<String> stack, Set<String> atomicFormulas) throws NotFormulaException {
        int countOfOperators = 0;
        int countOfFormulas = 0;
        StringBuilder atomicFormula = new StringBuilder();
        while (!stack.isEmpty() && !stack.peek().equals(Constants.LEFT_BRACKET)) {
            if (Constants.symbols.contains(stack.peek()) || Constants.numbers.contains(stack.peek())) {
                atomicFormula.insert(0, stack.peek());
                stack.pop();
                continue;
            }
            if (!atomicFormula.isEmpty()) {
                countOfFormulas++;
                atomicFormula = new StringBuilder();
            }
            if (Operations.getAllOperators().contains(stack.peek())) {
                countOfOperators++;
                stack.pop();
            }
        }
        if (!atomicFormula.isEmpty()) countOfFormulas++;

        if (countOfOperators > 1) throw new NotFormulaException();
        if (countOfFormulas == 1 && countOfOperators == 0) throw new NotFormulaException();
    }

    private static void checkPastCharacterForAlphabet(String symbol) throws NotFormulaException {
        if (symbol.equals(Constants.RIGHT_BRACKET)) throw new NotFormulaException();
    }
}