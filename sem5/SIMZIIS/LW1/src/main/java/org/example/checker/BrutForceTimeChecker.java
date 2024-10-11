package org.example.checker;

import org.example.generator.LowercaseLettersPasswordGenerator;
import org.example.generator.PasswordGenerator;
import org.jfree.chart.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BrutForceTimeChecker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private PasswordGenerator passwordGenerator = new LowercaseLettersPasswordGenerator();

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public Map<Integer, Long> checkBrutForceTime(int maxLength) {
        Map<Integer, Long> timeMap = new HashMap<>();
        for (int i = 1; i <= maxLength; i++) {
            String password = passwordGenerator.generatePassword(i);
            String brutForcedPassword = "";
            Instant startTime = Instant.now();
            while (!Objects.equals(password, brutForcedPassword)) {
                brutForcedPassword = brutForce(brutForcedPassword, i);
            }
            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);
            timeMap.put(i, duration.toMillis());
        }
        return timeMap;
    }

    private String brutForce(String oldPassword, int length){
        if (Objects.equals(oldPassword, "")) return "a".repeat(length);
        char[] passwordChars = oldPassword.toCharArray();
        int alphabetLength = ALPHABET.length();
        for (int i = length - 1; i >= 0; i--) {
            int currentCharIndex = ALPHABET.indexOf(passwordChars[i]);
            if (currentCharIndex < alphabetLength - 1) {
                passwordChars[i] = ALPHABET.charAt(currentCharIndex + 1);
                break;
            } else {
                passwordChars[i] = ALPHABET.charAt(0);
            }
        }
        return new String(passwordChars);
    }

}
