package FinCore.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import FinCore.helpers.*;

public class Withdraw extends Operations {
    // Intialising global variables
    public static Supporters supportersInstance = new Supporters();
    private static double balanceAfterTransaction = 0;

    public Withdraw(double currentBalance) {
        super(currentBalance);
    }

    @Override
    // Entry method to call upon the other methods
    public void displayText(Scanner scanner, double currentBalance) {
        if (FinCore.App.returnToMainMenu) {
            return;
        }

        IO.print("Enter amount to withdraw or enter mm to return to the main menu: $");

        // Error handling in case the user enters a letter
        try {
            double amountToWithdraw = scanner.nextDouble();

            // While loop to check they arent trying to add money
            while (amountToWithdraw < 0) {
                scanner.nextLine();

                IO.println("You cannot withdraw a negative value! Please try again!");
                displayText(scanner, currentBalance);
                return;
            }

            // While loop to check that the user is not withdrawing more than they have in
            // their account
            while (amountToWithdraw > currentBalance) {
                scanner.nextLine();

                IO.println("You cannot withdraw more than your current balance!");
                displayText(scanner, currentBalance);
                return;
            }

            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToWithdraw), currentBalance);
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                FinCore.App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println("Invalid Input. Please Try Again");
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
        String amountToWithdrawFormatted = String.format("Withdrawal successful! Your withdrew: $%.2f",
                amountToWithdraw);
        String newBalanceFormatted = String.format("Your new balance is: $%.2f", newBalance);

        IO.println("Withdrawal successful! Your withdrew: $" + amountToWithdrawFormatted);
        IO.println("Your new balance is: $" + newBalanceFormatted);
        balanceAfterTransaction = newBalance;
    }

    @Override
    // Method to return the new balance set after transaction
    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}