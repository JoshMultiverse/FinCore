package FinCore;

import FinCore.helpers.*;
import FinCore.operations.*;
import FinCore.log_in.*;
import java.util.*;

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
    public static String goodbyeMessage = "Thank you for using FinCore CLI Banking. Goodbye!";
    public static int userChoice;
    public static double currentBalance;
    public static boolean returnToMainMenu = false;
    private static int passwordAttempts = 3;

    // Creating a log in instance
    private static LogIn logInInstance = new LogIn();
    private static UserBalanceFileEditor fileEditorInstance = new UserBalanceFileEditor(0);

    // Main function
    public static void main(String[] args) {
        int counter = 0;
        // Get the user input
        Scanner scanner = new Scanner(System.in);

        // Try to log user in - limited to 3 attempts
        while (counter < passwordAttempts) {
            boolean areCredentialsCorrect = logInInstance.checkUserCredentials(scanner);

            // If their credentials are correct, break outside of the loop
            if (areCredentialsCorrect) {
                break;
            }
            // Increment the counter when the user gets uses a log in attempt
            counter += 1;
        }
        // Set the isExit variable so that if more than 3 attempts were made, the
        // program terminates.
        isExit = compareCounterToAttemptsMade(counter);

        // Main menu loop
        while (!isExit) {
            // Gets the global userEmail variable from the log in instance.
            currentBalance = logInInstance.getUserBalance(LogIn.userEmail);

            // Print the menu out + current balance
            IO.print("Welcome, " + LogIn.userEmail + "! Your current balance is $" + currentBalance);
            IO.print(Menu);

            // If the input is a number
            if (checkInputIsValid(scanner)) {
                // Reset the return to main menu value
                returnToMainMenu = false;
                // Call the setBankOperation function
                setBankOperations(userChoice, scanner);
            }
        }

        IO.println(goodbyeMessage);
    }

    static boolean compareCounterToAttemptsMade(int counter) {
        return counter == 3;
    }

    // Function to get the option selected by the user
    static void setBankOperations(int userChoice, Scanner scanner) {
        switch (userChoice) {
            case 3:
                // Display the current balance
                callCheckBalanceClass(userChoice);
                break;
            case 4:
                // Break the loop
                isExit = true;
                break;
            default:
                // While the user hasnt returned to the main menu
                while (!returnToMainMenu) {
                    List<Operations> operations = new ArrayList<>(); // List for the operation sub classes
                    List<Object[]> operationsObjectList = new ArrayList<>(); // List for the operation objects
                    int counter = 0;

                    // Create my two operation instances
                    Operations depositInstance = new Deposit(currentBalance);
                    Operations withdrawInstance = new Withdraw(currentBalance);

                    // Add them to the operations lists
                    operations.add(depositInstance);
                    operations.add(withdrawInstance);

                    // Create objects for the operations - adding the counter on as an identifier
                    for (Operations operation : operations) {
                        counter += 1;
                        operationsObjectList.add(new Object[] { counter, operation });
                    }

                    // Match if each object in the list is equal to the user choice, if so, then
                    // call the entry method (POLYMORPHISM)
                    for (Object[] operationObject : operationsObjectList) {
                        if ((int) operationObject[0] == userChoice) {
                            // Cast the object to the operations data type
                            Operations operation = (Operations) operationObject[1];
                            operation.displayText(scanner, currentBalance); // Polymorphism
                            // Set the current balance to the new value
                            currentBalance = operation.returnBalanceAfterTransaction();
                            // Overwrite the 'userBalances.csv' file to the new balance.
                            fileEditorInstance
                                    .ChangeBalance(Double.toString(operation.returnBalanceAfterTransaction()));
                        }
                    }
                    break;
                }
        }

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
