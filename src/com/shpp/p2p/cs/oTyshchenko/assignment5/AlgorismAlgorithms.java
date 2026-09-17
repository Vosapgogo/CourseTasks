package com.shpp.p2p.cs.oTyshchenko.assignment5;

import java.util.ArrayList;
import java.util.Scanner;

public class AlgorismAlgorithms {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            System.out.println("Enter first number:  ");
            String n1 = scanner.next();
            System.out.println("Enter second number:  ");
            String n2 = scanner.next();
            System.out.println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            System.out.println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private static String addNumericStrings(String n1, String n2) {
        ArrayList<Integer> number = new ArrayList<>();
        StringBuilder newN1 = new StringBuilder(n1);
        StringBuilder newN2 = new StringBuilder(n2);
        int prev=0;

        while (!newN1.isEmpty() || !newN2.isEmpty() || prev > 0) {
            int sum = prev;

            if (!newN1.isEmpty()) {
                sum += getLastDigit(newN1);
                deleteLastDigit(newN1);
            }

            if (!newN2.isEmpty()) {
                sum += getLastDigit(newN2);
                deleteLastDigit(newN2);
            }

            number.add(sum % 10);

            prev = sum / 10;
        }

        StringBuilder result = new StringBuilder();
        for (int i = number.size() - 1; i >= 0; i--) {
            result.append(number.get(i));
        }

        return result.toString();
    }

    private static int getLastDigit(StringBuilder str) {
        char ch = str.charAt(str.length() - 1);
        return ch - '0';
    }

    private static void deleteLastDigit(StringBuilder str) {
        str.deleteCharAt(str.length() - 1);
    }
}
