package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Scanner;

public class Assignment3Part3 {

    /**
     * Calculates the result of raising a base to a given exponent.
     * Supports positive, negative, and zero exponents.
     */
    private static double raiseToPower(double base, int exponent) {
        // Base case: Any number to the power of 0 is 1.0
        if (exponent == 0) {
            return 1.0;
        }

        // If the exponent is negative, invert the base and make the exponent positive
        if (exponent < 0) {
            return 1.0 / raiseToPower(base, -exponent);
        }

        // Recursive case for positive exponents: multiply base by the result of base^(exponent - 1)
        return base * raiseToPower(base, exponent - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a base: ");
        double base = scanner.nextDouble();

        System.out.print("Enter an exponent: ");
        int exponent = scanner.nextInt();

        // The method now handles everything internally, keeping the main method clean
        System.out.println(raiseToPower(base, exponent));
    }
}