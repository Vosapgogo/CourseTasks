package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Scanner;

public class Assignment3Part2 {
    private static final int TARGET_NUMBER = 1;

    private static final String ENTER_NUMBER = "Enter a number: ";
    private static final String ERROR_MESSAGE = "Error: Please enter a positive integer (1 or greater).\n";

    private static final String EVEN = "%d is even so I take half: %d\n";
    private static final String ODD = "%d is odd so I make 3n + 1: %d\n";

    private static final String RESULT_FORMAT = "It took %d steps to reach 1.\n";

    public static void main(String[] args) {
        // Initialize the scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);
        int num;

        // Prompt the user to enter the starting number
        while (true) {
            System.out.print(ENTER_NUMBER);
            num = scanner.nextInt();

            // If the user enters 1 or greater, the input is valid
            if (num >= TARGET_NUMBER) {
                break;
            }

            // Reject 0 or negative numbers
            System.out.print(ERROR_MESSAGE);
        }

        int steps = calculateHailstoneSequence(num);

        // Indicate that the sequence has finished and reached 1
        System.out.printf(RESULT_FORMAT, steps);
    }

    /**
     * Calculates the Hailstone sequence for a given number,
     * prints each step, and counts the total number of iterations.
     *
     * @param num the starting number
     * @return the total number of steps taken to reach 1
     */
    private static int calculateHailstoneSequence(int num) {
        int steps = 0;

        // Loop continues generating the sequence until the number reaches 1
        while (num > TARGET_NUMBER) {
            if (num % 2 == 0) {
                // Calculate next even number once and store it efficiently
                int nextNum = num / 2;
                System.out.printf(EVEN, num, nextNum);
                num = nextNum;
            } else {
                // Calculate next odd number once and store it efficiently
                int nextNum = num * 3 + 1;
                System.out.printf(ODD, num, nextNum);
                num = nextNum;
            }
            steps++;
        }

        return steps;
    }
}
