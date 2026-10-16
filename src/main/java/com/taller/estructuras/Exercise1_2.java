package com.taller.estructuras;
import java.util.Scanner;

    public class Exercise1_2 {
        public static void main(String[] args) {
            final double LIMIT = 1_000_000;
            double accumulation = 0;
            boolean process = true;
            String cause = "";

            Scanner scanner = new Scanner(System.in);

            while (process) {
                System.out.print("Type the transaction amount: ");
                double amount = scanner.nextDouble();

                if (amount < 0) {
                    process = false;
                    cause = "Invalid amount";
                } else {
                    accumulation += amount;
                    if (accumulation > LIMIT) {
                        process = false;
                        cause = "Amount surpassed";
                    }
                }
            }

            System.out.println("GENERAL REPORT");
            System.out.printf("Final amount: $%,.2f%n", accumulation);
            System.out.println("Ending cause: " + cause);

            scanner.close();
        }
    }


