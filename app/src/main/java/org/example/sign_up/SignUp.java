package org.example.sign_up;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import org.example.App;
import org.example.helpers.UserBalanceFileEditor;
import org.example.helpers.UserCredentialFileEditor;
import org.example.log_in.*;

// Class to get the user to sign up
public class SignUp {
    private String userEmailToBe = "";
    private static String passwordToBe = "";
    private String name = "";
    private int correctPasswordsEntered = 1;
    private boolean areThereAnyNullValues = true;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public boolean signUpForm(Scanner scanner) {
        while (areThereAnyNullValues) {
            IO.print(ANSI_BLUE + "Please enter your name: " + ANSI_RESET);
            name = scanner.nextLine();

            IO.print(ANSI_BLUE + "Please enter your email address: " + ANSI_RESET);
            userEmailToBe = scanner.nextLine();

            // Check if emaial already exists
            if (doesEmailExist(userEmailToBe)) {
                // Create Log In instance
                LogIn logInInstance = new LogIn();

                // Call the CheckUserCredentials method
                logInInstance.checkUserCredentials(scanner);
            }

            IO.print(ANSI_BLUE + "Please enter a password: " + ANSI_RESET);
            passwordToBe = scanner.nextLine();

            // Ensuring that they entered the password which they intended to.
            while (correctPasswordsEntered != 2) {
                IO.print(ANSI_BLUE + "Please enter your password again: " + ANSI_RESET);
                String passwordToCompare = scanner.nextLine();

                // Get the user to confirm their password
                if (doesPasswordMatch(passwordToCompare)) {
                    correctPasswordsEntered += 1;
                } else {
                    IO.println(ANSI_RED + "That was not the same as the previous password!" + ANSI_RESET);
                }
            }

            // Make a new string
            String[] inputsToCompare = new String[] { name, userEmailToBe, passwordToBe };

            // Check if there are any null values - if true loop will re run
            areThereAnyNullValues = checkForNullValues(inputsToCompare);
            areThereAnyNullValues = checkForIntegerValues(inputsToCompare);
        }

        try {
            return createUserAccount();
        } catch (Exception e) {
            System.out.println("Wrong file path");
            return false;
        }
    }

    private boolean createUserAccount() throws Exception {
        // Intialise a starting balance
        try {
            UserBalanceFileEditor userBalanceFileEditorInstance = new UserBalanceFileEditor(0);
            userBalanceFileEditorInstance.createObject(new String[] { userEmailToBe, "0" });
        } catch (Exception e) {
            throw new IOException("Failed to create user balance file");
        }
        // Generate a random sort code + account number - I know in a real application
        // this wouldnt be the case
        var random = new Random();

        // Generate random values
        int newSortCode = random.nextInt(100000, 999999);
        int newAccountNumber = random.nextInt(10_000_000, 99_999_999);

        // Convert numeric values to zero-padded strings so we can substring parts
        String sortCodeStr = String.format("%06d", newSortCode);
        String formattedSortCode = String.format("%s-%s-%s",
                sortCodeStr.substring(0, 2),
                sortCodeStr.substring(2, 4),
                sortCodeStr.substring(4, 6));
        String formattedAccountNumber = String.format("%08d", newAccountNumber);

        try (FileWriter bankDetailsWriter = new FileWriter(App.directoryPath + "/csv/bankDetails.csv", true)) {
            bankDetailsWriter
                    .write(name + "," + formattedSortCode + "," + formattedAccountNumber + "," + userEmailToBe + "\n");
        } catch (Exception e) {
            throw new IOException("Failed to write to file");
        }

        // Add the details to the userCredentials.csv file
        try {
            tryAddCredentialsToFile();
            LogIn.setEmail(userEmailToBe);
            return true;
        } catch (Exception eIoException) {
            throw new IOException("Failed to write to file");
        }
    }

    // Method which loops through the string array built and checks if each value is
    // null
    public static boolean checkForNullValues(String[] inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                System.out.println("Cannot have null value, please change it");
                return true;
            }
        }

        return false;
    }

    public static boolean checkForIntegerValues(String[] inputs) {
        for (String input : inputs) {
            try {
                // Try to parse as double as this is the most flexible type
                Double.parseDouble(input);

                System.out.println("Cannot enter a number - please try again");

                // If this works, user has not entered a string, return false
                return true;
            } catch (Exception e) {
                // Excpected Input - continue
                continue;
            }
        }

        return false;
    }

    public void tryAddCredentialsToFile() throws IOException {
        // Add the details to the userCredentials.csv file
        UserCredentialFileEditor userCredentialFileEditorIntance = new UserCredentialFileEditor();
        userCredentialFileEditorIntance.createObject(new String[] { name, userEmailToBe, passwordToBe });
    }

    // Method which returns if the password is equal to the most recent one entered.
    public static boolean doesPasswordMatch(String passwordToCompare) {
        return passwordToBe.equals(passwordToCompare);
    }

    public static boolean doesEmailExist(String emailToCheck) {
        // Call the method created in the LogIn.java file to do this
        return LogIn.doesEmailExist(emailToCheck);
    }
}
