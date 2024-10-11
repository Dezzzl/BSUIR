package org.example.checker;

import java.util.HashMap;
import java.util.Map;

public class PasswordCrackEstimator {

    private static final int ALPHABET_SIZE = 26;
    private static final double ATTACK_SPEED = 1_000_000_000;

    public Map<Integer, Long> estimateCrackTime(int maxPasswordLength) {
        Map<Integer, Long> crackTimeMap = new HashMap<>();

        for (int passwordLength = 1; passwordLength <= maxPasswordLength; passwordLength++) {
            double possibleCombinations = Math.pow(ALPHABET_SIZE, passwordLength);
            double crackTimeInSeconds = (possibleCombinations / ATTACK_SPEED);
            crackTimeMap.put(passwordLength, (long)crackTimeInSeconds);
        }

        return crackTimeMap;
    }
}


