package org.example.sign_up;

import java.io.FileWriter;
import java.io.IOException;
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
    private String passwordToBe = "";
    private String name = "";
    private int correctPasswordsEntered = 1;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    // TODO Build an array of all inputs and check if they can be converted

    public boolean signUpForm(Scanner scanner) throws InputMismatchException {
        while (userEmailToBe.isEmpty() && passwordToBe.isEmpty() & name.isEmpty()) {
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
        }

        // TODO Create a new method for this which throws a IOException

        // Intialise a starting balance
        try {
            UserBalanceFileEditor userBalanceFileEditorInstance = new UserBalanceFileEditor(0, new LogIn());
            userBalanceFileEditorInstance.createObject(new String[] { userEmailToBe, "0" });
        } catch (IOException e) {
            throw new IOException("Failed to create user balance file for: " + userEmailToBe, e);
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
        } catch (IOException e) {
            System.out.println("Failed to write to file");
            throw new IOException();
        }

        // Add the details to the userCredentials.csv file
        try {
            tryAddCredentialsToFile();
            LogIn.setEmail(userEmailToBe);
            return true;
        } catch (IOException eIoException) {
            eIoException.printStackTrace();
            throw new IOException();
        }
    }

    public void tryAddCredentialsToFile() throws IOException {
        // Add the details to the userCredentials.csv file
        UserCredentialFileEditor userCredentialFileEditorIntance = new UserCredentialFileEditor();
        userCredentialFileEditorIntance.createObject(new String[] { name, userEmailToBe, passwordToBe });
    }

    // Method which returns if the password is equal to the most recent one entered.
    public boolean doesPasswordMatch(String passwordToCompare) {
        return passwordToBe.equals(passwordToCompare);
    }

    public static boolean doesEmailExist(String emailToCheck) {
        // Call the method created in the LogIn.java file to do this
        return LogIn.doesEmailExist(emailToCheck);
    }
}
