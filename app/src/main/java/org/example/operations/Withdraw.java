package org.example.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.example.helpers.*;

public class Withdraw extends Operations {
    // Intialising global variables
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    // Intialising global variables
    public static Supporters supportersInstance = new Supporters();
    private static double balanceAfterTransaction = 0;

    public Withdraw(double currentBalance) {
        super(currentBalance);
    }

    @Override
    // Entry method to call upon the other methods
    public void displayText(Scanner scanner, double currentBalance) {
        if (org.example.App.returnToMainMenu) {
            return;
        }

        IO.print(ANSI_GREEN + "Enter amount to withdraw or enter mm to return to the main menu: $" + ANSI_RESET);

        // Error handling in case the user enters a letter
        try {
            double amountToWithdraw = scanner.nextDouble();

            // While loop to check they arent trying to add money
            while (amountToWithdraw < 0) {
                scanner.nextLine();

                IO.println(ANSI_RED + "You cannot withdraw a negative value! Please try again!" + ANSI_RESET);
                displayText(scanner, currentBalance);
                return;
            }

            // While loop to check that the user is not withdrawing more than they have in
            // their account
            while (amountToWithdraw > currentBalance) {
                scanner.nextLine();

                IO.println(ANSI_RED + "You cannot withdraw more than your current balance!" + ANSI_RESET);
                displayText(scanner, currentBalance);
                return;
            }

            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToWithdraw), currentBalance);
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine().toLowerCase())) {
                org.example.App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println(ANSI_RED + "Invalid Input. Please Try Again" + ANSI_RESET);
                displayText(scanner, currentBalance);
            }
        }
    }

    @Override
    // Method to subtract the two values from each other
    public void calculateNewBalance(double amountToWithdraw, double currentBalance) {
        printNewBalance(currentBalance - amountToWithdraw, amountToWithdraw, currentBalance);
    }

    // Method to print the new balance
    public static void printNewBalance(double newBalance, double amountToWithdraw, double currentBalance) {
        String amountToWithdrawFormatted = String.format(
                ANSI_GREEN + "Withdrawal successful! Your withdrew: $%.2f" + ANSI_RESET,
                amountToWithdraw);
        String newBalanceFormatted = String.format(ANSI_GREEN + "Your new balance is: $%.2f" + ANSI_RESET, newBalance);

        IO.println(ANSI_GREEN + "Withdrawal successful! Your withdrew: $" + amountToWithdrawFormatted + ANSI_RESET);
        IO.println(ANSI_GREEN + "Your new balance is: $" + newBalanceFormatted + ANSI_RESET);
        balanceAfterTransaction = newBalance;
    }

    @Override
    // Method to return the new balance set after transaction
    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}