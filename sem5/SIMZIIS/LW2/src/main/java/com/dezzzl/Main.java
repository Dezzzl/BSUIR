package com.dezzzl;

import com.dezzzl.cipher.AdvancedCaesarCipher;
import com.dezzzl.cipher.CaesarCipher;
import com.dezzzl.cipher.Cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cipher cipher = new CaesarCipher(0);
        while (true) {
            System.out.print("1 - Зашифровать текст\n2 - Расшифровать текст\n3- Выбрать шифр\n");
            String userChoice = scanner.nextLine();
            try {
                int choice = Integer.parseInt(userChoice);
                switch (choice){
                    case 1 -> {
                        System.out.print("Введите текст: ");
                        String text = scanner.nextLine();
                        System.out.println("Зашифровать текст: " + cipher.encode(text));
                    }
                    case 2 -> {
                        System.out.print("Введите текст для расшифровки: ");
                        String text = scanner.nextLine();
                        System.out.println("Расшифрованный текст: " + cipher.decode(text));
                        if (cipher instanceof AdvancedCaesarCipher) break;
                        System.out.println("Расшифровка с помощью перебора");
                        BrutForceAttacker brutForceAttacker = new BrutForceAttacker();
                        brutForceAttacker.attack(cipher.decode(text), text);
                    }
                    case 3 -> {
                        System.out.println("Выберите шифр:\n1- Обычный шифр Цезаря\n2- Модифицированный шифр Цезаря");
                        userChoice = scanner.nextLine();
                        choice = Integer.parseInt(userChoice);
                        System.out.println("Введите ключ(сдвиг по алфавиту)");
                        Integer key = scanner.nextInt();
                        scanner.nextLine();
                        switch(choice){
                            case 1 -> {
                                cipher = new CaesarCipher(key);
                            }
                            case 2 -> {
                                System.out.println("Введите сдвиги через пробел: ");
                                String shiftsInput = scanner.nextLine();
                                String[] shiftsArray = shiftsInput.split("\\s+");
                                List<Integer> shifts = new ArrayList<>();
                                for (String shift : shiftsArray) {
                                    shifts.add(Integer.parseInt(shift.trim()));
                                }

                                cipher = new AdvancedCaesarCipher(key, shifts);
                            }
                        }
                    }
                    default -> {
                        return;
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Нужно ввести число");
            }
        }
    }
}