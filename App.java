package FinCore;

import java.util.Scanner;

public class App {
    public static boolean addEntry = false;
    public static boolean viewSummary = false;
    public static boolean checkBalance = false;

    public static void main(String[] args) {
        System.out.println("""
                Welcome to FinCore! Select an option:
                1: Add Entry
                2: View Summary
                3: Check Balance
                    """);

        // Get the user input
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();

        // Check to make sure the user input is valid
        while (userChoice < 1 || userChoice > 3) {
            System.out.println("That input is invalid, please enter a value between 1 and 3 inclusive.");
            userChoice = scanner.nextInt();
        }

        // Shut the scanner and call the setBankOperation function
        scanner.close();
        setBankOperations(userChoice);

    }

    // Function to get the option selected by the user
    static void setBankOperations(int userChoice) {
        switch (userChoice) {
            case 1:
                // Change boolean value
                addEntry = true;
                break;
            case 2:
                // Change boolean value
                viewSummary = true;
                break;
            case 3:
                // Change boolean value
                checkBalance = true;
                break;
            default:
                break;
        }
    }
}
