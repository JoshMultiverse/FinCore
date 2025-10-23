package org.sign_up;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.helpers.UserBalanceFileEditor;
import org.helpers.UserCredentialFileEditor;
import org.log_in.*;

// Class to get the user to sign up
public class SignUp {
    private String userEmailToBe = "";
    private String passwordToBe = "";
    private String name = "";
    private int correctPasswordsEntered = 1;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    public boolean signUpForm(Scanner scanner) throws InputMismatchException {
        while (userEmailToBe.isEmpty() && passwordToBe.isEmpty() & name.isEmpty()) {
            try {
                IO.println(ANSI_BLUE + "Please enter your name" + ANSI_RESET);
                name = scanner.nextLine();

                IO.println(ANSI_BLUE + "Please enter your email address" + ANSI_RESET);
                userEmailToBe = scanner.nextLine();

                // Check if emaial already exists
                if (doesEmailExist(userEmailToBe)) {
                    // Create Log In instance
                    LogIn logInInstance = new LogIn();

                    // Call the CheckUserCredentials method
                    logInInstance.checkUserCredentials(scanner);
                }

                IO.println(ANSI_BLUE + "Please enter a password" + ANSI_RESET);
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

            } catch (InputMismatchException eInputMismatchException) {
                throw new Error(eInputMismatchException);
            }
        }

        // Intialise a starting balance
        try {
            UserBalanceFileEditor userBalanceFileEditorInstance = new UserBalanceFileEditor(0, new LogIn());
            userBalanceFileEditorInstance.IntialiseUserAccount(userEmailToBe, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add the details to the userCredentials.csv file
        try {
            return tryAddCredentialsToFile();
        } catch (IOException eIoException) {
            eIoException.printStackTrace();
            return false;
        }
    }

    public boolean tryAddCredentialsToFile() throws IOException {
        // Add the details to the userCredentials.csv file
        UserCredentialFileEditor userCredentialFileEditorIntance = new UserCredentialFileEditor(name,
                userEmailToBe, passwordToBe);
        return userCredentialFileEditorIntance.AppendUserCredentialsToFile();
    }

    // Method which returns if the password is equal to the most recent one entered.
    public boolean doesPasswordMatch(String passwordToCompare) {
        return passwordToBe.equals(passwordToCompare);
    }

    public boolean doesEmailExist(String emailToCheck) {
        // Call the method created in the LogIn.java file to do this
        return LogIn.doesEmailExist(emailToCheck);
    }
}
