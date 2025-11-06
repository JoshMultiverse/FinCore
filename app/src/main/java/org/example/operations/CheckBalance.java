package org.example.operations;

import org.example.log_in.LogIn;

public class CheckBalance {
    // Prints the current balance saved
    public void displayCurrentBalance(double currentBalance) {
        LogIn logInInstance = new LogIn();

        currentBalance = logInInstance.getUserBalance(logInInstance.getEmail());
        IO.println("Your current balance is: $" + currentBalance);
    }
}