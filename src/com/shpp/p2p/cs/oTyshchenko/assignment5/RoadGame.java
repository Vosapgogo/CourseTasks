package com.shpp.p2p.cs.oTyshchenko.assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RoadGame {
    private static final String DICTIONARY_FILE = "src/com/shpp/p2p/cs/oTyshchenko/assignment5/en-dictionary.txt";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            System.out.print("Enter 3 letters:  ");
            String threeLetters = scanner.next().toLowerCase();
            System.out.println();

            String word = wordFinding(threeLetters);

            if (word == null) {
                System.out.println("No word found containing " + threeLetters + " in order.");
            } else {
                System.out.println("You got this word: " + word);
            }
            System.out.println();
        }
    }

    /**
     * Searches the dictionary for a word that contains the given letters
     * in the same order (not necessarily consecutively).
     *
     * @param threeLetters three letters to search for, in order.
     * @return the first matching word, or null if none is found.
     */
    private static String wordFinding(String threeLetters) {
        try (BufferedReader br = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (containsLettersInOrder(line.toLowerCase(), threeLetters)) {
                    return line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        return null;
    }

    /**
     * Checks whether the given letters appear in the word in the same
     * order, though not necessarily next to each other.
     */
    private static boolean containsLettersInOrder(String word, String letters) {
        int position = 0;

        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            boolean found = false;

            for (int j = position; j < word.length(); j++) {
                if (letter == word.charAt(j)) {
                    position = j + 1;
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }
}
