package com.dezzzl.cipher;

import java.util.ArrayList;
import java.util.List;

public class CaesarCipher extends Cipher {

    protected Integer shift;

    protected static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";


    public CaesarCipher(Integer shift) {
        this.shift = shift;
    }

    @Override
    public String encode(String publicText) {
        StringBuilder encodedText = new StringBuilder();
        String preparedPublicText = preparePublicTextToEncode(publicText);
        for (char c : preparedPublicText.toCharArray()) {
            char encodedCharacter = ALPHABET.charAt((ALPHABET.indexOf(c) + shift) % ALPHABET.length());
            encodedText.append(encodedCharacter);
        }
        return encodedText.toString();
    }

    @Override
    public String decode(String ciphertext) {
        StringBuilder decodedText = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            char decodedCharacter = ALPHABET.charAt((ALPHABET.indexOf(c) - shift + ALPHABET.length()) % ALPHABET.length());
            decodedText.append(decodedCharacter);
        }
        return decodedText.toString();
    }

    protected String preparePublicTextToEncode(String publicText) {
        return publicText.toLowerCase().replaceAll("[^а-я]", "");
    }

    public static void main(String[] args) {
        List<Integer> shifts = List.of(5,7,13,21,3);
        List<String> text = List.of("Прием, как меня слышно", "Зашифруй этот текст", "Шифр Цезаря", "ключ шифрования", "Пример");

        for(int i = 0; i< 5; i++) {
            Cipher cipher = new CaesarCipher(shifts.get(i));
            String encodedText = cipher.encode(text.get(i));
            String decodedText = cipher.decode(encodedText);
            System.out.println("Сдвиг: "+ shifts.get(i));
            System.out.println("Зашифрованный текст: "+encodedText);
            System.out.println("Расшифрованный текст "+decodedText);
        }
    }


}
