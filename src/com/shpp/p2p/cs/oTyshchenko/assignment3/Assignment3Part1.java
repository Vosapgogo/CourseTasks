package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Scanner;

public class Assignment3Part1 {
    private static final String QUESTION = "How many minutes did you do on day %d? ";
    private static final String ERROR = "Error: A day cannot have more than 1440 minutes! Please try again.";

    private static final String CARDIOVASCULAR_CATEGORY_NAME = "Cardiovascular health:";
    private static final String SUCCESS_CARDIOVASCULAR = "Great job! You've done enough exercise for cardiovascular health.";
    private static final String NOT_ENOUGH_CARDIOVASCULAR = "You needed to train hard for at least %d more day(s) a week!\n";
    private static final int NEEDED_CARDIOVASCULAR = 5;
    private static final int NEEDED_CARDIOVASCULAR_TIME = 30;

    private static final String BLOOD_PRESSURE_CATEGORY_NAME = "Blood pressure:";
    private static final String SUCCESS_BLOOD_PRESSURE = "Great job! You've done enough exercise to keep a low blood pressure.";
    private static final String NOT_ENOUGH_BLOOD_PRESSURE = "You needed to train hard for at least %d more day(s) a week!\n";
    private static final int NEEDED_BLOOD_PRESSURE = 3;
    private static final int NEEDED_BLOOD_PRESSURE_TIME = 40;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect exercise data for 7 days
        int[] exerciseDays = collectExerciseData(scanner);

        int cardiovascularHealth = exerciseDays[0];
        int bloodPressure = exerciseDays[1];

        // Print results for cardiovascular health
        printHealthResult(CARDIOVASCULAR_CATEGORY_NAME, cardiovascularHealth, NEEDED_CARDIOVASCULAR, SUCCESS_CARDIOVASCULAR, NOT_ENOUGH_CARDIOVASCULAR);

        // Print results for blood pressure
        printHealthResult(BLOOD_PRESSURE_CATEGORY_NAME, bloodPressure, NEEDED_BLOOD_PRESSURE, SUCCESS_BLOOD_PRESSURE, NOT_ENOUGH_BLOOD_PRESSURE);
    }

    /**
     * Prompts the user for minutes exercised over 7 days
     * and counts qualifying days for cardio and blood pressure.
     * @return an array where index 0 is cardio days and index 1 is blood pressure days.
     */
    private static int[] collectExerciseData(Scanner scanner) {
        int bloodPressure = 0;
        int cardiovascularHealth = 0;

        for (int i = 1; i <= 7; i++) {
            int time;

            while (true) {
                System.out.printf(QUESTION, i);
                time = scanner.nextInt();

                if (time >= 0 && time <= 1440) {
                    break;
                }

                System.out.println(ERROR);
            }

            if (time>=NEEDED_BLOOD_PRESSURE_TIME){
                bloodPressure++;
            }

            if (time>=NEEDED_CARDIOVASCULAR_TIME) {
                cardiovascularHealth++;
            }
        }

        cardiovascularHealth += bloodPressure;

        return new int[] {cardiovascularHealth, bloodPressure};
    }

    /**
     * Evaluates and prints health results for a given category.
     */
    private static void printHealthResult(String categoryName, int actualDays, int neededDays, String successMessage, String notEnoughMessage) {
        System.out.println(categoryName + ":");
        if (actualDays >= neededDays) {
            System.out.println(successMessage);
        } else {
            int missingDays = neededDays - actualDays;
            System.out.printf(notEnoughMessage, missingDays);
        }
    }
}
