package org.example;

public class OperationResolver {
    public static boolean denialOperation(boolean first, boolean second) {
        return first && !second;
    }

    public static boolean disjunction(boolean first, boolean second) {
        return first || second;
    }

    public static boolean pierceOperation(boolean first, boolean second) {
        return !(first || second);
    }

    public static boolean implicationFirstToSecond(boolean first, boolean second) {
        return !first || second;
    }
}
