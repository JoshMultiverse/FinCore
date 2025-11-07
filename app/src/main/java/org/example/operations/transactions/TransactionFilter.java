package main.java.org.example.operations.transactions;

import java.lang.classfile.Superclass;
import java.util.*;

import org.example.App;
import org.example.operations.transactions.TransactionHistory;

public class TransactionFilter extends TransactionHistory {
    // Attributes
    private static Map<String, LinkedList<String>> transactionHistory;
    private static double lowerBound;
    private static double upperBound;
    private static double rangeUserWantsToFilter;
    private static double userLowerBoundInput;
    private static double userUpperBoundInput;

    // Constructor
    public TransactionFilter(String email) {
        super(email);
        transactionHistory = super.getIfUserHasTransactionHistory() ? super.getHashMapContents() : null;
    }

    // Method to set the bounds that the user can select
    public static void setBounds() {
        if (transactionHistory == null) {
            System.out.println("You have no history to filter! ");
            return;
        }

        LinkedList<String> transactions = transactionHistory.get(TransactionHistory.getUserEmail());

        // Loop through the hashmap and split each value using a splitter
        for (String transaction : transactions) {
            // Split the string to get individual values
            String[] balanceValues = transaction.split(";");

            // Find the difference between the two values
            double differenceBetweenValues = findDifferenceBetweenValues(balanceValues);

            // Check if both bounds have been set
            if (lowerBound == 0.0 && upperBound == 0.0) {
                lowerBound = upperBound = differenceBetweenValues;
            }

            // Compare to the lower bound
            if (differenceBetweenValues < lowerBound) {
                lowerBound = differenceBetweenValues;
            }

            // Compare to upper bound
            if (differenceBetweenValues > upperBound) {
                upperBound = differenceBetweenValues;
            }
        }
    }

    // Method to get the difference based on transaction
    public static Double findDifferenceBetweenValues(String[] balanceValues) {
        double temp = 0;

        // Perform subtraction based on the type - prevent negatives
        switch (balanceValues[0]) {
            case "deposit" -> {
                temp = Double.parseDouble(balanceValues[2])
                        - Double.parseDouble(balanceValues[1]);
            }
            default -> {
                temp = upperBound = Double.parseDouble(balanceValues[1])
                        - Double.parseDouble(balanceValues[2]);
            }
        }

        return temp;
    }

    // Method to get the bounds from the user themseleves
    public static void getBoundsFromUser(Scanner scanner) {
        while (true) {
            System.out.println("Lower bound: " + lowerBound + "\n Upper bound: " + upperBound);

            System.out.println(App.ANSI_BLUE + "Please enter the lower bound for your filter");

            try {
                userLowerBoundInput = scanner.nextDouble();
                scanner.nextLine(); // <-- Consume next line

                // If the input is less than the lowest possible value
                if (userLowerBoundInput < lowerBound) {
                    // Print a message saying the user cannot have a lower input than the bound set
                    System.out.println(App.ANSI_RED + "You cannot have a lower bound less than " + lowerBound);
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println(App.ANSI_RED + "Please enter a numerical value!");
            }

            // Get the upper bound from the filter
            try {
                System.out.println("Please enter the upper bound for your filter");
                userUpperBoundInput = scanner.nextDouble();
                scanner.nextLine();

                // Check if upper bound is greater than actual variable
                if (userUpperBoundInput > upperBound) {
                    System.out.println(App.ANSI_RED + "You cannot have a upper bound more than " + upperBound);
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println(App.ANSI_RED + "Please enter a numerical value! ");
            }
        }
    }

    public static void displayTransactionResults() {
        LinkedList<String> transactions = transactionHistory.get(TransactionHistory.getUserEmail());

        for (String transaction : transactions) {
            String[] transactionComponents = transaction.split(";");

            if (isTransactionWithinBounds(transactionComponents)) {
                System.out.println((transactionComponents[0].equals("deposit") ? App.ANSI_GREEN : App.ANSI_RED)
                        + "Transaction Type: " + transactionComponents[0] + "\n" +
                        "Old Balance: $" + transactionComponents[1] + "\n" +
                        "New Balance: $" + transactionComponents[2] + "\n" + App.ANSI_RESET);
                System.out.println("-------------------------");
            }
        }
    }

    // Method to check if the transaction is within bounds.
    public static boolean isTransactionWithinBounds(String[] transaction) {
        double differenceToCompare;

        switch (transaction[0]) {
            case "deposit" -> {
                // Set the difference variable
                differenceToCompare = Double.parseDouble(transaction[1]) - Double.parseDouble(transaction[2]);
            }

            default -> {
                // Set the difference variable
                differenceToCompare = Double.parseDouble(transaction[2]) - Double.parseDouble(transaction[1]);
            }
        }

        // If difference is within range, return true.
        if (differenceToCompare >= lowerBound && differenceToCompare <= upperBound) {
            return true; // <-- Within range
        }

        return false;
    }
}