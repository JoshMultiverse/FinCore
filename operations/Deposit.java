package FinCore.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import FinCore.helpers.*;

public class Deposit {
    // Intialising global variables
    protected static double amountToDeposit;
    protected static double balanceAfterTransaction;
    protected static double currentBalance;
    public static Supporters supportersInstance = new Supporters();

    public Deposit(double currentBalance) {
        Deposit.currentBalance = currentBalance;
    }

    // Entry method to call upon the other methods
    public void displayDepositText(Scanner scanner) {
        if (FinCore.App.returnToMainMenu) {
            return;
        }

        IO.print("Enter amount to deposit or enter 'mm' to return to the main menu: $");

        // Error handling in case the user enters a letter
        try {
            double amountToDeposit = scanner.nextDouble();

            // While loop to check they arent trying to add money
            while (amountToDeposit < 0) {
                scanner.nextLine();

                IO.println("You cannot deposit negative values! Please try again!");
                displayDepositText(scanner);
                return;
            }
            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToDeposit));
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                FinCore.App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println("Invalid Input. Please Try Again");
                displayDepositText(scanner);
                return;
            }
        }
    }

    // Method to subtract add two values from each other
    public static void calculateNewBalance(double amountToDeposit) {
        printNewBalance(currentBalance + amountToDeposit, amountToDeposit);
    }

    // Method to print the new balance and the amount deposited
    public static void printNewBalance(double newBalance, double amountToDeposit) {
        IO.println("Deposit successful! You deposited: $" + amountToDeposit);
        IO.println("Your new balance is: $" + newBalance);
        balanceAfterTransaction = newBalance;
    }

    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
}