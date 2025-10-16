package FinCore.operations;

import java.util.InputMismatchException;
import java.util.Scanner;
import FinCore.helpers.*;

public class Deposit extends Operations {
    // Intialising global variables
    public static Supporters supportersInstance = new Supporters();
    private static double balanceAfterTransaction = 0;

    public Deposit(double currentBalance) {
        super(currentBalance);
    }

    // Entry method to call upon the other methods
    public void displayText(Scanner scanner, double currentBalance) {
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
                displayText(scanner, currentBalance);
                return;
            }
            // Call this method to calculate the balance. Truncate the value entered to 2DP.
            calculateNewBalance(supportersInstance.truncateTo2DP(amountToDeposit), currentBalance);
        } catch (InputMismatchException eInputMismatchException) {
            if (supportersInstance.isReturnToMainMenu(scanner.nextLine())) {
                FinCore.App.returnToMainMenu = true;
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

    // Method to subtract add two values from each other
    public void calculateNewBalance(double amountToDeposit, double currentBalance) {
        IO.println(currentBalance);
        printNewBalance(currentBalance + amountToDeposit, amountToDeposit, currentBalance);
    }

    // Method to print the new balance and the amount deposited
    public static void printNewBalance(double newBalance, double amountToDeposit, double currentBalance) {
        IO.println("Deposit successful! You deposited: $" + amountToDeposit);
        IO.println("Your new balance is: $" + newBalance);
        balanceAfterTransaction = newBalance;
    }

    // Method to return the new balance set after transaction
    public double returnBalanceAfterTransaction() {
        IO.println(balanceAfterTransaction);
        return balanceAfterTransaction;
    }
}