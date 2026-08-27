package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Random;

public class Assignment3Part5 {
    public static void main(String[] args) {
        // Track the total money earned across all games
        int total = 0;
        // Track the total number of games played
        int gameCount = 0;

        // Outer loop: continue playing until the total earnings reach or exceed $20
        while (total < 20) {
            // Starting pot for each individual game is always $1
            int pot = 1;

            // Inner loop: flip the coin as long as it lands on heads (true)
            // Each heads doubles the amount in the pot
            while (coinFlip()) {
                pot *= 2;
            }

            // When tails (false) appears, the game ends and the pot goes to the player
            gameCount++;
            total += pot;

            System.out.println("This game, you earned $" + pot);
            System.out.println("Your total is $" + total);
        }

        System.out.println("It took " + gameCount + " games to earn $20");
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