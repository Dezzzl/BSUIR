package org.example;

import org.example.checker.BrutForceTimeChecker;
import org.example.checker.FrequencyDistributionChecker;
import org.example.checker.PasswordCrackEstimator;
import org.example.generator.LowercaseLettersPasswordGenerator;
import org.example.generator.PasswordGenerator;
import org.example.plot.BrutForceTimePlot;
import org.example.plot.FrequencyDistributionPlot;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PasswordGenerator passwordGenerator = new LowercaseLettersPasswordGenerator();
        System.out.println(passwordGenerator.generatePassword(12)   );
        FrequencyDistributionChecker frequencyDistributionChecker = new FrequencyDistributionChecker();
        FrequencyDistributionPlot.display(frequencyDistributionChecker.checkFrequency(10, 10));
        BrutForceTimeChecker brutForceTimeChecker = new BrutForceTimeChecker();
        BrutForceTimePlot.display(brutForceTimeChecker.checkBrutForceTime(5));
        PasswordCrackEstimator passwordCrackEstimator = new PasswordCrackEstimator();
        passwordCrackEstimator.estimateCrackTime(15);
        BrutForceTimePlot.display(passwordCrackEstimator.estimateCrackTime(15));
    }
}