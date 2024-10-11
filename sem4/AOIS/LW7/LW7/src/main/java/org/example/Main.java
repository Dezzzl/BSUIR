package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Matrix matrix = new Matrix();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        String choice = """
                1 - вставить значение по индексу
                2 - получить значение по индексу
                3 - вставить слово по индексу столбца
                4 - получить слово по индексу столбца
                5 - вставить адресный столбец по индексу строки
                6 - получить адресный столбец по значеню строки
                7 - выполнить операцию над двумя словами
                8 - выполнить операцию над двумя адресными столбцами
                9 - арифметичкая операция над словами по ключу
                10 - поисковая операция по словам, находящимся в отрезке двух слов
                11 - вывести матрицу
                12 - выйти из программы
                """;
do {

    System.out.println("Выберите операцию:");
    System.out.println(choice);

    int operation = scanner.nextInt();
    String word, columnAddress;

    switch (operation) {
        case 1:
            System.out.println("Введите номер строки: ");
            int row = scanner.nextInt();
            System.out.println("Введите номер столбца: ");
            int column = scanner.nextInt();
            System.out.println("Введите значение: ");
            int value = scanner.nextInt();
            matrix.setValue(row, column, value);
            break;
        case 2:
            System.out.println("Введите номер строки: ");
            row = scanner.nextInt();
            System.out.println("Введите номер столбца: ");
            column = scanner.nextInt();
            int result = matrix.getValue(row, column);
            System.out.println("Значение по индексу " + "{ " + row + " , " + column + " }" + ": " + result);
            break;
        case 3:
            System.out.println("Введите номер столбца: ");
            column = scanner.nextInt();
            System.out.println("Введите слово: ");
            word = getBinaryStringInput();
            matrix.setWord(column, word);
            break;
        case 4:
            System.out.println("Введите номер столбца: ");
            column = scanner.nextInt();
            String wordResult = matrix.getWord(column);
            System.out.println("Слово по столбцу " + column + ": " + wordResult);
            break;
        case 5:
            System.out.println("Введите номер строки: ");
            row = scanner.nextInt();
            System.out.println("Введите адресный столбец: ");
            columnAddress = getBinaryStringInput();
            matrix.setAddressColumn(row, columnAddress);
            break;
        case 6:
            System.out.println("Введите номер строки: ");
            row = scanner.nextInt();
            String columnResult = matrix.getAddressColumn(row);
            System.out.println("Адресный столбец по номеру строки " + row + ": " + columnResult);
            break;
        case 7:
            System.out.println("Введите номер столбца 1 слова: ");
            int column1 = scanner.nextInt();
            System.out.println("Введите номер столбца 2 слова: ");
            int column2 = scanner.nextInt();
            System.out.println("Введите номер столбца, куда будет записан результат: ");
            int column3 = scanner.nextInt();
            System.out.println("Выберите операцию");
            Operation operation1 = selectOperation();
            matrix.wordOperation(column1, column2, column3, operation1);
            break;
        case 8:
            System.out.println("Введите номер строки 1 адресного столбца: ");
            int row1 = scanner.nextInt();
            System.out.println("Введите номер строки 2 адресного столбца: ");
            int row2 = scanner.nextInt();
            System.out.println("Введите номер строки, куда будет записан результат: ");
            int row3 = scanner.nextInt();
            System.out.println("Выберите операцию");
            operation1 = selectOperation();
            matrix.addressColumnOperation(row1, row2, row3, operation1);
            break;
        case 9:
            System.out.println("Введите ключ (000 - 111)");
            scanner.nextLine();
            String key = scanner.nextLine();
            matrix.arithmeticOperations(key);
            break;
        case 10:
            System.out.println("Введите 1-ое слово");
            String firstWord = getBinaryStringInput();
            System.out.println("Введите 2-ое слово");
            String secondWord = getBinaryStringInput();
            List<String> wordsInRange = matrix.getWordsInRange(firstWord, secondWord);
            System.out.println("Найденные слова:\n");
            for (String wordInRange : wordsInRange) {
                System.out.println(wordInRange);
            }
            break;
        case 11:
            matrix.printMatrix();
            break;
        case 12:
            exit = true;
            break;
        default:
            System.out.println("Некорректный выбор.");
            break;
    }
} while (!exit);

        scanner.close();
    }

    public static String getBinaryStringInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isValid = false;

        do {
            System.out.print("Введите строку из 16 символов, содержащую только 0 или 1: ");
            input = scanner.nextLine();

            // Проверяем, что длина строки равна 16
            if (input.length() != 16) {
                System.out.println("Некорректная длина строки. Пожалуйста, введите строку из 16 символов.");
                continue;
            }

            // Проверяем, что все символы строки являются '0' или '1'
            isValid = true;
            for (char c : input.toCharArray()) {
                if (c != '0' && c != '1') {
                    isValid = false;
                    break;
                }
            }

            if (!isValid) {
                System.out.println("Некорректный ввод. Пожалуйста, введите только 0 или 1.");
            }
        } while (!isValid);

        return input;
    }

    public static Operation selectOperation() {
        Scanner scanner = new Scanner(System.in);
        Operation selectedOperation = null;

        System.out.println("Выберите операцию:");
        System.out.println("1. Запрет 1-го аргумента (НЕТ)");
        System.out.println("2. Дизъюнкция (ИЛИ)");
        System.out.println("3. Операция Пирса(ИЛИ-НЕ)");
        System.out.println("4. Импликация от 1-го аргумента ко 2-му (НЕТ-НЕ)");

        int choice = scanner.nextInt();

        selectedOperation = switch (choice) {
            case 1 -> Operation.DENIAL_OPERATION;
            case 2 -> Operation.DISJUNCTION;
            case 3 -> Operation.PIERCE_OPERATION;
            default -> Operation.IMPLICATION_FIRST_TO_SECOND;
        };

        return selectedOperation;
    }
}