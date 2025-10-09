package FinCore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Deposit {
    public static double amountToDeposit;

    public void displayDepositText(double currentBalance, Scanner scanner) {
        IO.print("Enter amount to deposit: $");

        try {
            double amountToDeposit = scanner.nextDouble();

            while (amountToDeposit < 0) {
                scanner.nextLine();

                IO.println("You cannot deposit negative values! Please try again!");
                displayDepositText(currentBalance, scanner);
            }

            calculateNewBalance(currentBalance, truncateTo2DP(amountToDeposit));
        } catch (InputMismatchException eInputMismatchException) {
            scanner.nextLine();

            IO.println("Invalid Input. Please Try Again");
            displayDepositText(currentBalance, scanner);
        }
    }

    public static double truncateTo2DP(double amountToDeposit) {
        return Math.floor(amountToDeposit * 100) / 100;
    }

    public static void calculateNewBalance(double currentBalance, double amountToDeposit) {
        printNewBalance(currentBalance + amountToDeposit);
    }

    public static void printNewBalance(double newBalance) {
        IO.println("Your new balance is: $" + newBalance);
    }
}