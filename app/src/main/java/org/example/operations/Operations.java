package org.example.operations;

import java.util.Scanner;

import org.example.helpers.Supporters;

// My super class which will contain the abstract classes for the sub classes to use
public abstract class Operations {
    protected double currentBalance;
    public static Supporters supportersInstance = new Supporters();

    public Operations(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public abstract void displayText(Scanner scanner, double currentBalance);

    public abstract void calculateNewBalance(double userOperationValue, double currentBalance);

    public abstract double returnBalanceAfterTransaction();
}
