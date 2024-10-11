package com.dezzzl;

import static com.dezzzl.PrimitiveElementFinder.powerMod;

public class SecretGenerator {
    public static long generateOpenSecret(long g, long P, long a) {
        return powerMod(g, a, P);
    }

    public static long generateSharedSecret(long a, long P, long A) {
        return powerMod(A, a, P);
    }
}
