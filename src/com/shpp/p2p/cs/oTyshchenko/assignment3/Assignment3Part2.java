package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Scanner;

public class Assignment3Part2 {
    public static void main(String[] args) {
        // Initialize the scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the starting number
        System.out.print("Enter a number: ");
        int num = scanner.nextInt();

        // Loop continues generating the Hailstone sequence until the number reaches 1
        while (num > 1) {
            // Check if the current number is even
            if (num % 2 == 0) {
                // If even, print the calculation and divide the number by 2
                System.out.printf("%d is even so I take half: %d\n", num, (num / 2));
                num = num / 2;
            } else {
                // If odd, print the calculation, multiply by 3, and add 1
                System.out.printf("%d is odd so I make 3n + 1: %d\n", num, (num * 3 + 1));
                num = num * 3 + 1;
            }
        }

        // Indicate that the sequence has finished and reached 1
        System.out.println("End");
    }
}
