package com.dezzzl;

import java.util.Random;

import static com.dezzzl.PrimitiveElementFinder.findPrimitiveRoot;
import static com.dezzzl.SecretGenerator.generateOpenSecret;
import static com.dezzzl.SecretGenerator.generateSharedSecret;

public class Main {
    public static void main(String[] args) {
        // Вариант 21, P = 4877
        long P = 4877L;

        long g = findPrimitiveRoot(P);
        System.out.println("g-примитивный элемент: " + g);

        Random rand = new Random();
        long a = rand.nextInt((int) P - 1) + 1;
        System.out.println("a: " + a);
        long b = rand.nextInt((int) P - 1) + 1;
        System.out.println("b: " + b);

        long A = generateOpenSecret(g, P, a);
        System.out.println("A (открытый ключ A): " + A);

        long B = generateOpenSecret(g, P, b);
        System.out.println("B (открытый ключ B): " + B);

        long K_A = generateSharedSecret(a, P, B);
        System.out.println("K_A (общий секрет у A): " + K_A);

        long K_B = generateSharedSecret(b, P, A);
        System.out.println("K_B (общий секрет у B): " + K_B);
    }
}
