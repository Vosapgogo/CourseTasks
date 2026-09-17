package com.shpp.p2p.cs.oTyshchenko.assignment5;

import java.util.Scanner;

public class SyllableCounting {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            System.out.print("Enter a single word (or 'quit' to exit): ");
            String word = scanner.next();

            if (word.equalsIgnoreCase("quit")) {
                break;
            }

            System.out.println("Syllable count: " + syllablesInWord(word) + " syllables");
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private static int syllablesInWord(String word) {
        char[] wordArray = word.toLowerCase().toCharArray();
        int numSyllables = 0;

        for (int i = 0; i < wordArray.length; i++) {
            if (isVowel(wordArray[i])) {
                if (i==0 || !isVowel(wordArray[i-1])) {
                    numSyllables++;
                }
            }
        }

        int lastIndex = wordArray.length - 1;
        if (wordArray[lastIndex] == 'e'
                && (lastIndex == 0 || !isVowel(wordArray[lastIndex - 1]))) {
            numSyllables--;
        }

        return Math.max(1, numSyllables);
    }

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y';
    }
}
