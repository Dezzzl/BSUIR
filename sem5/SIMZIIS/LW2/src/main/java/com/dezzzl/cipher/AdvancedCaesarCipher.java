package com.dezzzl.cipher;

import java.util.List;

public class AdvancedCaesarCipher extends CaesarCipher{

    private final List<Integer> shifts;

    public AdvancedCaesarCipher(Integer shift, List<Integer> shifts) {
        super(shift);
        this.shifts = shifts;
    }

    @Override
    public String encode(String publicText) {
        StringBuilder encodedText = new StringBuilder();
        String preparedPublicText = preparePublicTextToEncode(publicText);
        int shiftsIndex = 0;
        for (char c : preparedPublicText.toCharArray()) {
            char encodedCharacter = ALPHABET.charAt((ALPHABET.indexOf(c) + shift + shifts.get(shiftsIndex++)) % ALPHABET.length());
            encodedText.append(encodedCharacter);
            if (shiftsIndex==shifts.size())shiftsIndex = 0;
        }
        return encodedText.toString();
    }

    @Override
    public String decode(String ciphertext) {
        StringBuilder decodedText = new StringBuilder();
        int shiftsIndex = 0;
        for (char c : ciphertext.toCharArray()) {
            int decodedIndex = (ALPHABET.indexOf(c) - shift - shifts.get(shiftsIndex++) + ALPHABET.length()) % ALPHABET.length();
            if (decodedIndex < 0) {
                decodedIndex += ALPHABET.length();
            }
            char decodedCharacter = ALPHABET.charAt(decodedIndex);
            decodedText.append(decodedCharacter);
            if (shiftsIndex==shifts.size())shiftsIndex = 0;
        }
        return decodedText.toString();
    }

    public static void main(String[] args) {
        List<Integer> shifts = List.of(5,7,13,21,3);
        List<String> text = List.of("Прием, как меня слышно", "Зашифруй этот текст", "Шифр Цезаря", "ключ шифрования", "Пример");
        System.out.println("Сдвиги: ");
        shifts.stream()
                .forEach(shift -> System.out.print(shift + " "));
        System.out.println();


        for(int i = 0; i< 5; i++) {
            Cipher cipher = new AdvancedCaesarCipher(shifts.get(i), List.of(3,5,6,-3,-4,12,10));
            String encodedText = cipher.encode(text.get(i));
            String decodedText = cipher.decode(encodedText);
            System.out.println("Сдвиг: "+ shifts.get(i));
            System.out.println("Зашифрованный текст: "+encodedText);
            System.out.println("Расшифрованный текст "+decodedText);
        }
    }
}
