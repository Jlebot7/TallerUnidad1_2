package com.taller.estructuras;
import java.util.Scanner;

public class Exercise1_1 {
    public static void main(String[] args) {
        int sum = 0;
        int counter = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Type a number (-99 to exit): ");
        int number = scanner.nextInt();

        while (number != -99){
            sum += number;
            counter++;

            System.out.println("Type a number (-99 to exit): ");
            number = scanner.nextInt();
        }
        System.out.println("The sum is " + sum);
        System.out.println("The counter is " + counter);
    }
}
