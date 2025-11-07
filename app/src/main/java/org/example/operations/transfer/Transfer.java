package org.example.operations.transfer;

import java.util.Scanner;

import org.example.App;
import org.example.helpers.UserBalanceFileEditor;
import org.example.log_in.LogIn;
import org.example.operations.Operations;

public class Transfer extends Operations {
    private static double balanceAfterTransaction;
    private static String sortCode;
    private static String accountNumber;
    private String transfererEmail;
    private String accountHoldersName;
    private double amountToTransfer;

    public Transfer(double currentBalance, String email) {
        super(currentBalance);
        this.transfererEmail = email;
    }

    // Entry method for this class
    @Override
    public void displayText(Scanner scanner, double currentBalance) {
        var transferOperationsInstance = new TransferOperations(transfererEmail, currentBalance);

        // Get both the sort code and account number
        while (true) {
            try {
                System.out.print(App.ANSI_BLUE + "Please enter the sort code of the account you wish to transfer to: ");
                sortCode = scanner.nextLine();

                // Check if sort code is valid
                if (isSortCodeValid()) {
                    // Get the account number
                    System.out.print("Please enter the account number of the account you wish to transfer to: ");
                    accountNumber = scanner.nextLine();

                    // Check the account number format is valid
                    if (isAccountNumberValid()) {
                        // Get the email address of the person they wish to transfer to
                        System.out.print("Please enter the account holders name: " + App.ANSI_RESET);
                        accountHoldersName = scanner.nextLine().trim().toLowerCase();

                        // Check if these details match a record
                        boolean doesAccountExist = transferOperationsInstance.doesTransfereeExist(accountHoldersName,
                                sortCode,
                                accountNumber);

                        // If the account does not exist, then ask the user if they want to try again
                        if (!doesAccountExist) {
                            if (doesUserWantToTryAgain(scanner)) {
                                continue;
                            } else {
                                System.out.println(App.ANSI_BLUE + "Returning to the main menu...." + App.ANSI_RESET);
                                break;
                            }
                        }

                        // Process transfer if the account exists in the file

                        // Check if the amount to transfer is greater than the current balance, not
                        // possible
                        while (true) {
                            amountToTransfer = transferOperationsInstance.getTransferAmount(scanner);

                            // Get if user trying to transfer more than they currently have
                            if (amountToTransfer > currentBalance) {
                                if (doesUserWantToTryAgain(scanner)) {
                                    continue;
                                }

                                // Return if they dont want to try again
                                return;
                            } else {
                                break;
                            }
                        }

                        // Confirm the user wants to do the transaction?
                        if (confirmUserWantsToDoTransaction(scanner, amountToTransfer)) {
                            // Check if the account exists in userBalances.csv
                            if (transferOperationsInstance.doesValueExistInUserBalances()) {
                                // Begin to process transfer
                                LogIn.setEmail(transfererEmail);

                                // Subtract the amount to transfer away from the current balance
                                calculateNewBalance(amountToTransfer, currentBalance);

                                // Deposit the money to the transferee
                                transferOperationsInstance.readAllLinesAfterTargetLine(amountToTransfer);

                                // Withdraw from transferer
                                var userBalanceInstance = new UserBalanceFileEditor(0);
                                userBalanceInstance.readFile(Double.toString(currentBalance - amountToTransfer));

                                // return now operation is complete
                                return;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        System.out.println("Account number format inavlid!");
                    }
                } else {
                    System.out.println("Please enter a 6 digit sort code!");
                }

            } catch (Exception e) {
                System.out.println("Please enter a 6 digit sort code!");
            }
        }

    }

    @Override
    // Method to subtract add two values from each other
    public void calculateNewBalance(double amountToTransfer, double currentBalance) {
        printNewBalance(currentBalance - amountToTransfer, amountToTransfer, currentBalance);
    }

    // Method to print the new balance and the amount deposited
    public static void printNewBalance(double newBalance, double amountToTransfer, double currentBalance) {
        String amountToDepositFomatted = String
                .format(App.ANSI_GREEN + "Transfer successful! You transferred: $%.2f" + App.ANSI_RED,
                        amountToTransfer);
        String newBalanceFormatted = String.format(App.ANSI_GREEN + "Your new balance is: $%.2f" + App.ANSI_RESET,
                newBalance);
        IO.println(amountToDepositFomatted);
        IO.println(newBalanceFormatted);
        balanceAfterTransaction = newBalance;
    }

    @Override
    public double returnBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public boolean isSortCodeValid() {
        // Check if the sort code is length of 6 (UK Standard)
        if (sortCode.length() == 6) {
            setSortCode(String.format("%s-%s-%s",
                    sortCode.substring(0, 2),
                    sortCode.substring(2, 4),
                    sortCode.substring(4, 6)));
            return true;
        } else {
            System.out.println("Sort code must be a length of 6!");
            return false;
        }
    }

    public boolean isAccountNumberValid() {
        return accountNumber.length() == 8;
    }

    // Method to check if the user wants to try again
    public boolean doesUserWantToTryAgain(Scanner scanner) {
        System.out.print(App.ANSI_RED + "Process failed! Press y to try again" + App.ANSI_RESET);
        String userChoice = scanner.nextLine().toLowerCase();

        if (userChoice.equals("y")) {
            return true;
        }

        return false;
    }

    public boolean confirmUserWantsToDoTransaction(Scanner scanner, double amountToTransfer) {
        String formattedTransferText = String.format("$%.2f", amountToTransfer);

        System.out.println("--------------------------------------------------");
        System.out.println("""
                        You are about to transfer %s to the following account:
                        %s
                        %s
                        %s
                """.formatted(formattedTransferText, accountHoldersName, accountNumber, sortCode));
        System.out.println("--------------------------------------------------");
        System.out.print("Please enter y to confirm: ");

        return scanner.nextLine().trim().toLowerCase().equals("y");
    }

    public static void setSortCode(String value) {
        sortCode = value;
    }

    public static void setAccountNumber(String value) {
        accountNumber = value;
    }
}
