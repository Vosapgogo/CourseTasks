package com.shpp.p2p.cs.oTyshchenko.assignment3;

import java.util.Scanner;

public class Assignment3Part1 {
    static final String SUCCESS_CARDIOVASCULAR = "Great job! You've done enough exercise for cardiovascular health.";
    static final String NOT_ENOUGH_CARDIOVASCULAR = "You needed to train hard for at least %d more day(s) a week!\n";
    static final int NEEDED_CARDIOVASCULAR = 5;
    static final int NEEDED_CARDIOVASCULAR_TIME = 30;

    static final String SUCCESS_BLOOD_PRESSURE = "Great job! You've done enough exercise to keep a low blood pressure.";
    static final String NOT_ENOUGH_BLOOD_PRESSURE = "You needed to train hard for at least %d more day(s) a week!\n";
    static final int NEEDED_BLOOD_PRESSURE = 3;
    static final int NEEDED_BLOOD_PRESSURE_TIME = 40;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Collect exercise data for 7 days
        int[] exerciseDays = collectExerciseData(scanner);

        int cardiovascularHealth = exerciseDays[0];
        int bloodPressure = exerciseDays[1];

        // Print results for cardiovascular health
        printCardiovascularResult(cardiovascularHealth);

        // Print results for blood pressure
        printBloodPressureResult(bloodPressure);
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
            System.out.printf("How many minutes did you do on day %d? ", i);
            int time = scanner.nextInt();

            if (time>=NEEDED_BLOOD_PRESSURE_TIME){
                bloodPressure++;
            } else if (time>=NEEDED_CARDIOVASCULAR_TIME) {
                cardiovascularHealth++;
            }
        }

        cardiovascularHealth += bloodPressure;

        return new int[] {cardiovascularHealth, bloodPressure};
    }

    /**
     * Evaluates and prints the cardiovascular health results.
     */
    private static void printCardiovascularResult(int cardioDays) {
        System.out.println("Cardiovascular health:");
        if (cardioDays>=NEEDED_CARDIOVASCULAR) {
            System.out.println(SUCCESS_CARDIOVASCULAR);
        } else {
            int missingDays = NEEDED_CARDIOVASCULAR-cardioDays;
            System.out.printf(NOT_ENOUGH_CARDIOVASCULAR, missingDays);
        }
    }

    /**
     * Evaluates and prints the blood pressure results.
     */
    private static void printBloodPressureResult(int bloodPressureDays) {
        System.out.println("Blood pressure:");
        if (bloodPressureDays>=NEEDED_BLOOD_PRESSURE) {
            System.out.println(SUCCESS_BLOOD_PRESSURE);
        } else {
            int missingDays = NEEDED_BLOOD_PRESSURE-bloodPressureDays;
            System.out.printf(NOT_ENOUGH_BLOOD_PRESSURE, missingDays);
        }
    }
}
