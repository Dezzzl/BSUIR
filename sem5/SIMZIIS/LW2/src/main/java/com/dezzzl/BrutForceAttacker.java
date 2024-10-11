package com.dezzzl;

import com.dezzzl.cipher.CaesarCipher;
import com.dezzzl.cipher.Cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BrutForceAttacker {
    private final Integer alphabetSize = 33;

    public void attack(String decodedText, String encodedText) {
        Map<Integer, String> allDecodedTexts = new HashMap<>();
        for (int i = 0; i <= alphabetSize; i++) {
            Cipher cipher = new CaesarCipher(i);
            allDecodedTexts.put(i, cipher.decode(encodedText));
        }
        System.out.println(allDecodedTexts.entrySet().stream()
                .map(e -> e.getKey()+" "+e.getValue())
                .collect(Collectors.joining("\n")));
        if (allDecodedTexts.containsValue(decodedText)) {
            allDecodedTexts.entrySet().stream()
                    .filter(e->e.getValue().equals(decodedText))
                    .findFirst()
                    .ifPresent(e-> System.out.println("Сдвиг: %d\nРасшифрованный текст: %s".formatted(e.getKey(), e.getValue())));
        }
        else System.out.println("Текст не удалось расшифровать");
    }

    public static void main(String[] args) {
        Cipher cipher = new CaesarCipher(23);
        String encodedText = cipher.encode("Текст");
        String decodedText = cipher.decode(encodedText);
        BrutForceAttacker attacker = new BrutForceAttacker();
        attacker.attack(decodedText, encodedText);
    }
}
