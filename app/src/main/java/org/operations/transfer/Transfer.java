package org.operations.transfer;

import java.util.Scanner;

import org.App;
import org.operations.Operations;

public class Transfer extends Operations {
    private double balanceAfterTransaction;
    private String sortCode;
    private String accountNumber;
    private String transfererEmail;
    private String accountHoldersName;

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
                        if (doesAccountExist) {
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
                            double amountToTransfer = transferOperationsInstance.getTransferAmount(scanner);

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
                        if (confirmUserWantsToDoTransaction(scanner, currentBalance)) {
                            // Check if the account exists in userBalances.csv
                            if (transferOperationsInstance.doesValueExistInUserBalances()) {
                                // System.out.println(true);
                            }

                            // Begin to process transfer

                        } else {
                            return;
                        }
                    } else {
                        System.out.println("Account number format inavlid!");
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    @Override
    public void calculateNewBalance(double amountToTransfer, double currentBalance) {

    }

    @Override
    public double returnBalanceAfterTransaction() {
        return 0.0;
    }

    private boolean isSortCodeValid() {
        // Check if the sort code is length of 6 (UK Standard)
        if (sortCode.length() == 6) {
            sortCode = String.format("%s-%s-%s",
                    sortCode.substring(0, 2),
                    sortCode.substring(2, 4),
                    sortCode.substring(4, 6));
            return true;
        } else {
            System.out.println("Sort code must be a length of 6!");
            return false;
        }
    }

    private boolean isAccountNumberValid() {
        return accountNumber.length() == 8;
    }

    // Method to check if the user wants to try again
    private boolean doesUserWantToTryAgain(Scanner scanner) {
        System.out.println(App.ANSI_RED + "Process failed! Press y to try again" + App.ANSI_RESET);
        String userChoice = scanner.nextLine().toLowerCase();

        if (userChoice.equals("y")) {
            return true;
        }

        return false;
    }

    private boolean confirmUserWantsToDoTransaction(Scanner scanner, double amountToTransfer) {
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
}
