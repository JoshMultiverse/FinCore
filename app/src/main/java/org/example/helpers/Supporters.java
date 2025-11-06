package org.example.helpers;

import java.util.InputMismatchException;

public class Supporters {
    public boolean isReturnToMainMenu(String stringEntered) {
        return stringEntered.equals("mm");
    }

    // Method to truncate the user input to 2DP (Maximium allowed with money)
    public double truncateTo2DP(double valueToTruncate) {
        return Math.floor(valueToTruncate * 100) / 100;
    }

    // Method to return the new balance
    public double returnNewBalance(double balanceAfterTransaction) {
        return balanceAfterTransaction;
    }

    // Method to check if the input can be converted to a number
    public static boolean doesInputConvertIntoNumber(String userInput) {
        try {
            Double.parseDouble(userInput);

            System.out.println("Cannot have a number! ");

            // return false if error isnt thrown
            return false;
        } catch (InputMismatchException e) {
            // This is what we want here - return true
            return true;
        }
    }
}