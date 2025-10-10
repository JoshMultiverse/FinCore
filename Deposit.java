package FinCore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Deposit {
    // Intialising global variables
    public static double amountToDeposit;
    public static double balanceAfterTransaction;
    public static Supporters supportersInstance = new Supporters();

    // Entry method to call upon the other methods
    public void displayDepositText(double currentBalance, Scanner scanner) {
        if (App.returnToMainMenu) {
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
                displayDepositText(currentBalance, scanner);
                return;
            }
            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(currentBalance, truncateTo2DP(amountToDeposit));
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                App.returnToMainMenu = true;
                return;
            } else {
                scanner.nextLine();

                // Call function recursively until the right input is reached
                IO.println("Invalid Input. Please Try Again");
                displayDepositText(currentBalance, scanner);
                return;
            }
        }
    }

    // Method to truncate the user input to 2DP (Maximium allowed with money)
    public static double truncateTo2DP(double amountToDeposit) {
        return Math.floor(amountToDeposit * 100) / 100;
    }

    // Method to subtract add two values from each other
    public static void calculateNewBalance(double currentBalance, double amountToDeposit) {
        printNewBalance(currentBalance + amountToDeposit, amountToDeposit);
    }

    // Method to print the new balance and the amount deposited
    public static void printNewBalance(double newBalance, double amountToDeposit) {
        IO.println("Deposit successful! You deposited: $" + amountToDeposit);
        IO.println("Your new balance is: $" + newBalance);
        balanceAfterTransaction = newBalance;
    }

    // Method to return the new balance
    public double returnNewBalance() {
        return balanceAfterTransaction;
    }
}