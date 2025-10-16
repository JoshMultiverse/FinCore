package FinCore.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import FinCore.helpers.*;

public class Withdraw {
    // Intialising global variables
    protected static double amountToWithdraw;
    protected static double balanceAfterTransaction;
    protected static double currentBalance;
    public static Supporters supportersInstance = new Supporters();

    public Withdraw(double currentBalance) {
        Withdraw.currentBalance = currentBalance;
    }

    // Entry method to call upon the other methods
    public void displayWithdrawText(Scanner scanner) {
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
                displayWithdrawText(scanner);
                return;
            }

            // While loop to check that the user is not withdrawing more than they have in
            // their account
            while (amountToWithdraw > currentBalance) {
                scanner.nextLine();

                IO.println("You cannot withdraw more than your current balance!");
                displayWithdrawText(scanner);
                return;
            }

            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToWithdraw));
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                FinCore.App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println("Invalid Input. Please Try Again");
                displayWithdrawText(scanner);
            }
        }
    }

    // Method to truncate the user input to 2DP (Maximium allowed with money)
    public static double truncateTo2DP() {
        return Math.floor(amountToWithdraw * 100) / 100;
    }

    // Method to subtract the two values from each other
    public static void calculateNewBalance(double amountToWithdraw) {
        printNewBalance(currentBalance - amountToWithdraw);
    }

    // Method to print the new balance
    public static void printNewBalance(double newBalance) {
        IO.println("Withdrawal successful! Your withdrew: $" + amountToWithdraw);
        IO.println("Your new balance is: $" + newBalance);
        balanceAfterTransaction = newBalance;
    }

    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}