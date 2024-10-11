package com.dezzzl;

import java.util.HashSet;
import java.util.Set;

public class PrimitiveElementFinder {
    public static boolean isPrimitiveRoot(long g, long P) {
        Set<Long> set = new HashSet<>();
        for (int i = 1; i < P; i++) {
            long powMod = powerMod(g, i, P);
            set.add(powMod);
        }
        return set.size() == P - 1;
    }

    public static long powerMod(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp % 2) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        return result;
    }

    public static long findPrimitiveRoot(long P) {
        for (long g = 2; g < P; g++) {
            if (isPrimitiveRoot(g, P)) {
                return g;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        long primitiveRoot = findPrimitiveRoot(4877);
        System.out.println(primitiveRoot);
    }
}
