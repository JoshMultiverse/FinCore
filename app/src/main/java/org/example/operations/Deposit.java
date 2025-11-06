package org.example.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.example.helpers.*;

public class Deposit extends Operations {
    // Intialising global variables
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    public static Supporters supportersInstance = new Supporters();
    private static double balanceAfterTransaction = 0;

    public Deposit(double currentBalance) {
        super(currentBalance);
    }

    @Override
    // Entry method to call upon the other methods
    public void displayText(Scanner scanner, double currentBalance) {
        if (org.example.App.returnToMainMenu) {
            return;
        }

        IO.print(ANSI_GREEN + "Enter amount to deposit or enter 'mm' to return to the main menu: $" + ANSI_RESET);

        // Error handling in case the user enters a letter
        try {
            double amountToDeposit = scanner.nextDouble();

            // While loop to check they arent trying to add money
            while (amountToDeposit < 0) {
                scanner.nextLine();

                IO.println(ANSI_RED + "You cannot deposit negative values! Please try again!" + ANSI_RESET);
                displayText(scanner, currentBalance);
                return;
            }
            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToDeposit), currentBalance);
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                org.example.App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println("Invalid Input. Please Try Again");
                displayText(scanner, currentBalance);
                return;
            }
        }
    }

    @Override
    // Method to subtract add two values from each other
    public void calculateNewBalance(double amountToDeposit, double currentBalance) {
        printNewBalance(currentBalance + amountToDeposit, amountToDeposit, currentBalance);
    }

    // Method to print the new balance and the amount deposited
    public static void printNewBalance(double newBalance, double amountToDeposit, double currentBalance) {
        String amountToDepositFomatted = String
                .format(ANSI_GREEN + "Deposit successful! You deposited: $%.2f" + ANSI_RED, amountToDeposit);
        String newBalanceFormatted = String.format(ANSI_GREEN + "Your new balance is: $%.2f" + ANSI_RESET, newBalance);
        IO.println(amountToDepositFomatted);
        IO.println(newBalanceFormatted);
        balanceAfterTransaction = newBalance;
    }

    @Override
    // Method to return the new balance set after transaction
    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}