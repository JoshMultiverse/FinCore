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

    public boolean signUpForm(Scanner scanner) throws InputMismatchException {
        while (userEmailToBe.isEmpty() && passwordToBe.isEmpty() & name.isEmpty()) {
            try {
                IO.println("Please enter your name");
                name = scanner.nextLine();

                IO.println("Please enter your email address");
                userEmailToBe = scanner.nextLine();

                // Check if emaial already exists
                if (doesEmailExist(userEmailToBe)) {
                    // Create Log In instance
                    LogIn logInInstance = new LogIn();

                    // Call the CheckUserCredentials method
                    logInInstance.checkUserCredentials(scanner);
                }

                IO.println("Please enter a password");
                passwordToBe = scanner.nextLine();

                // Ensuring that they entered the password which they intended to.
                while (correctPasswordsEntered != 2) {
                    IO.print("Please enter your password again: ");
                    String passwordToCompare = scanner.nextLine();

                    // Get the user to confirm their password
                    if (doesPasswordMatch(passwordToCompare)) {
                        correctPasswordsEntered += 1;
                    } else {
                        IO.println("That was not the same as the previous password!");
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
