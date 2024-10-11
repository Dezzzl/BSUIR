package org.example.checker;

import org.example.generator.LowercaseLettersPasswordGenerator;
import org.example.generator.PasswordGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyDistributionChecker {
    private PasswordGenerator passwordGenerator = new LowercaseLettersPasswordGenerator();

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public Map<String, Integer> checkFrequency(int length, int numOfPasswords){
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (int i =0; i< numOfPasswords; i++){
            String password = passwordGenerator.generatePassword(length);
            for (char c : password.toCharArray()){
                String symbol = Character.toString(c);
                frequencyMap.put(symbol, frequencyMap.getOrDefault(symbol, 0) + 1);
            }
        }
        return frequencyMap;
    }
}
