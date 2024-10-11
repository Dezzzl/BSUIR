package org.example.generator;

public class LowercaseLettersPasswordGenerator extends PasswordGenerator {
    private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.min(Math.random() * alphabet.length(), alphabet.length()-1));
            password.append(alphabet.charAt(randomIndex));
        }
        return password.toString();
    }
}
