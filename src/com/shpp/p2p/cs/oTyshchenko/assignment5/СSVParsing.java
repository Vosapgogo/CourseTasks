package com.shpp.p2p.cs.oTyshchenko.assignment5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class СSVParsing {
    private static final String FILE = "src/com/shpp/p2p/cs/oTyshchenko/assignment5/food-origins.csv";
    private static final int NUMBER_OF_COLUMNS = 2;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
            System.out.println(extractColumn(FILE, i));
        }
    }

    private static ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String value = fieldsIn(line).get(columnIndex);
                if (!value.isEmpty()) {
                    result.add(value);
                }
            }
        } catch (IOException e) {
            return null;
        }

        return result;
    }

    private static ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (insideQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        currentField.append('"');
                        i++;
                    } else {
                        insideQuotes = false;
                    }
                } else {
                    currentField.append(c);
                }
            } else {
                if (c == '"') {
                    insideQuotes = true;
                } else if (c == ',') {
                    fields.add(currentField.toString());
                    currentField = new StringBuilder();
                } else {
                    currentField.append(c);
                }
            }
        }

        fields.add(currentField.toString());
        return fields;
    }
}
