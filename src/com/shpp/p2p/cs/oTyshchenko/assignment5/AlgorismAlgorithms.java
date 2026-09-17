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
            System.out.println(n1 + " * " + n2 + " = " + multiplyNumericStrings(n1, n2));
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

            sum += popLastDigit(newN1);
            sum += popLastDigit(newN2);

            number.add(sum % 10);

            prev = sum / 10;
        }

        StringBuilder result = new StringBuilder();
        for (int i = number.size() - 1; i >= 0; i--) {
            result.append(number.get(i));
        }

        return result.toString();
    }

    private static String multiplyNumericStrings(String n1, String n2) {
        if (n1.equals("0") || n2.equals("0")) {
            return "0";
        }

        int len1 = n1.length();
        int len2 = n2.length();
        int[] result = new int[len1 + len2];

        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                int digit1 = n1.charAt(i) - '0';
                int digit2 = n2.charAt(j) - '0';

                int product = digit1 * digit2;

                int pos1 = i + j;
                int pos2 = i + j + 1;

                int sum = product + result[pos2];

                result[pos2] = sum % 10;
                result[pos1] += sum / 10;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int digit : result) {
            if (!(sb.length() == 0 && digit == 0)) {
                sb.append(digit);
            }
        }

        return sb.toString();
    }

    private static int popLastDigit(StringBuilder str) {
        if (!str.isEmpty()) {
            int digit = getLastDigit(str);
            deleteLastDigit(str);
            return digit;
        }
        return 0;
    }

    private static int getLastDigit(StringBuilder str) {
        char ch = str.charAt(str.length() - 1);
        return ch - '0';
    }

    private static void deleteLastDigit(StringBuilder str) {
        str.deleteCharAt(str.length() - 1);
    }
}
