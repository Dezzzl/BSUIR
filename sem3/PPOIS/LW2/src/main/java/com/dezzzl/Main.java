package com.dezzzl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import static com.dezzzl.DbManager.open;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = open()) {
            System.out.println(connection.getTransactionIsolation());
            SearchEngine searchEngine =new SearchEngine(connection);
            int a=0;
            while(a>=0) {
                int n = 1;
                searchEngine.getAllImages(a);
                System.out.println("PREV   NEXT");
                Scanner sc =new Scanner(System.in);
                String s= sc.next();
                if(Objects.equals(s, "NEXT"))a++;
                else a--;
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        }
    }
}
