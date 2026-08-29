package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Random;

public class Assignment3Part5 {
    // Game rules constants
    private static final int TARGET_EARNINGS = 20;
    private static final int INITIAL_POT = 1;
    private static final int POT_MULTIPLIER = 2;

    // Output message constants
    private static final String MSG_GAME_EARNINGS = "This game, you earned $%d\n";
    private static final String MSG_TOTAL_EARNINGS = "Your total is $%d\n";
    private static final String MSG_FINAL_RESULT = "It took %d games to earn $%d\n";

    public static void main(String[] args) {
        // Track the total money earned across all games
        int total = 0;
        // Track the total number of games played
        int gameCount = 0;

        // Outer loop: continue playing until the total earnings reach or exceed $20
        while (total < TARGET_EARNINGS) {
            // Starting pot for each individual game is always $1
            int pot = INITIAL_POT;

            // Inner loop: flip the coin as long as it lands on heads (true)
            // Each heads doubles the amount in the pot
            while (coinFlip()) {
                pot *= POT_MULTIPLIER;
            }

            // When tails (false) appears, the game ends and the pot goes to the player
            gameCount++;
            total += pot;

            System.out.printf(MSG_GAME_EARNINGS, pot);
            System.out.printf(MSG_TOTAL_EARNINGS, total);
        }

        System.out.printf(MSG_FINAL_RESULT, gameCount, TARGET_EARNINGS);
    }

    /**
     * Simulates a coin flip with a 50/50 chance.
     * @return true for heads, false for tails.
     */
    public static boolean coinFlip() {
        Random random = new Random();
        return random.nextBoolean();
    }
}