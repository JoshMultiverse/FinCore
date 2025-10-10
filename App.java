package FinCore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static boolean isExit = false;
    public static String Menu = """
            \n---------------Welcome to FinCore!----------------
                1: Deposit
                2: Withdraw
                3: Check Balance
                4: Exit
            ----------------------------------------------------

            Please select an option:  """;
    public static int userChoice;
    public static double currentBalance = 1000;
    public static boolean returnToMainMenu = false;

    // Create a withdraw instance
    public static Withdraw withdrawInstance = new Withdraw();

    // Create a new deposit instance
    public static Deposit depositInstance = new Deposit();

    // Main function
    public static void main(String[] args) {
        // Get the user input
        Scanner scanner = new Scanner(System.in);

        while (!isExit) {
            // Print the menu out
            IO.print(Menu);

            // If the input is a number
            if (checkInputIsValid(scanner)) {
                // Reset the return to main menu value
                returnToMainMenu = false;
                // Call the setBankOperation function
                setBankOperations(userChoice, scanner);
            }
        }
    }

    // Function to get the option selected by the user
    static void setBankOperations(int userChoice, Scanner scanner) {
        switch (userChoice) {
            case 1:
                while (!returnToMainMenu) {
                    // Call deposit class
                    callDepositClass(currentBalance, scanner);
                    // update the current balance
                    currentBalance = depositInstance.returnNewBalance();
                    break;
                }

                break;
            case 2:
                while (!returnToMainMenu) {
                    // Call withdraw
                    callWithdrawClass(currentBalance, scanner);
                    currentBalance = withdrawInstance.returnNewBalance();
                    break;
                }

                break;
            case 3:
                // Call check balance
                callCheckBalanceClass(currentBalance);
                break;
            case 4:
                IO.println("Thank you for using FinCore CLI Banking. Goodbye!");
                isExit = true;
                break;
            default:
                break;
        }
    }

    public static void callDepositClass(double currentBalance, Scanner scanner) {
        // Call the entry function to the class
        depositInstance.displayDepositText(currentBalance, scanner);
    }

    public static void callWithdrawClass(double currentBalance, Scanner scanner) {
        // Call the entry method to the class
        withdrawInstance.displayWithdrawText(currentBalance, scanner);
    }

    public static void callCheckBalanceClass(double currentBalance) {
        // Create an instance of the check balance class
        CheckBalance checkBalanceInstance = new CheckBalance();

        // Call the entry method to this class
        checkBalanceInstance.displayCurrentBalance(currentBalance);
    }

    // Function to check if the input entered is valid
    public static boolean checkInputIsValid(Scanner scanner) {
        // Use execption handling in case the user enters a text value
        try {
            // If the value is an int, pass to the next function to check if it is in the
            // correct range
            userChoice = scanner.nextInt();
            return checkInputIsInRange(userChoice, scanner);

        } catch (InputMismatchException eInputMismatchException) {
            // This code will run if the user enters letters/symbols etc.
            scanner.nextLine();

            IO.println("Please enter a number");
            IO.println(Menu);
            return checkInputIsValid(scanner); // Call the function recursively
        }
    }

    // Method to check that the input is within the desired range
    public static boolean checkInputIsInRange(int userChoice, Scanner scanner) {
        // Use a while loop to constantly check if the value is under 1 or over 4
        while (userChoice < 1 || userChoice > 4) {
            IO.println("That input is invalid, please enter a value between 1 and 4 inclusive.");
            IO.println(Menu);
            userChoice = scanner.nextInt();
        }

        return true;
    }
}
