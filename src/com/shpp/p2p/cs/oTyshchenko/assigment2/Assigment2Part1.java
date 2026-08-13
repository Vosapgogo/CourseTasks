package com.shpp.p2p.cs.oTyshchenko.assigment2;

import java.util.Scanner;

public class Assigment2Part1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a: ");
        double a = scanner.nextDouble();

        System.out.print("Please enter b: ");
        double b = scanner.nextDouble();

        System.out.print("Please enter c: ");
        double c = scanner.nextDouble();

        if (a==0) {
            System.out.println("This is not a quadratic equation (a cannot be 0)");
        } else {
            double discriminant = (b * b) - (4 * a * c);

            if (discriminant > 0) {
                double sqrtDiscriminant = Math.sqrt(discriminant);
                double firstRoot = (-b + sqrtDiscriminant) / (2 * a);
                double secondRoot = (-b - sqrtDiscriminant) / (2 * a);
                System.out.printf("There are two roots: %.1f and %.1f\n", firstRoot, secondRoot);
            } else if (discriminant == 0) {
                double onlyRoot = -b / (2 * a);

                if (onlyRoot == -0.0) {
                    onlyRoot = 0.0;
                }

                System.out.printf("There is one root: %.1f\n", onlyRoot);
            } else System.out.println("There are no real roots");
        }

        scanner.close();
    }
}
